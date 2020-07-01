package matsu.jippi.game;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import matsu.jippi.enumeration.slpreader.Command;
import matsu.jippi.enumeration.slpreader.SlpInputSource;
import matsu.jippi.pojo.common.ConversionType;
import matsu.jippi.pojo.common.FrameBookendType;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.GameEndType;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.ItemUpdateType;
import matsu.jippi.pojo.common.MetadataType;
import matsu.jippi.pojo.common.OverallType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PlayerInput;
import matsu.jippi.pojo.common.PostFrameUpdateType;
import matsu.jippi.pojo.common.PreFrameUpdateType;
import matsu.jippi.pojo.common.StatsType;
import matsu.jippi.pojo.common.StockType;
import matsu.jippi.pojo.slpreader.SlpFileType;
import matsu.jippi.pojo.slpreader.SlpReadInput;
import matsu.jippi.stats.ActionsComputer;
import matsu.jippi.stats.ComboComputer;
import matsu.jippi.stats.ConversionComputer;
import matsu.jippi.stats.InputComputer;
import matsu.jippi.stats.OverallActions;
import matsu.jippi.stats.Stats;
import matsu.jippi.stats.StatsQuerier;
import matsu.jippi.stats.StockComputer;
import matsu.jippi.util.SlpParser;
import matsu.jippi.util.SlpReader;
import matsu.jippi.interfaces.EventCallbackFunc;
import matsu.jippi.interfaces.EventPayloadTypes;

public class SlippiGame {

    private SlpReadInput input;
    private MetadataType metadata;
    private StatsType finalStats;
    private SlpParser parser;
    private Integer readPosition;
    private ActionsComputer actionsComputer = new ActionsComputer();
    private ConversionComputer conversionComputer = new ConversionComputer();
    private ComboComputer comboComputer = new ComboComputer();
    private StockComputer stockComputer = new StockComputer();
    private InputComputer inputComputer = new InputComputer();
    private Stats statsComputer = new Stats(null);

    private SlpReader slpReader = new SlpReader();

    public SlippiGame(String gameInput) {
        input = new SlpReadInput(SlpInputSource.FILE, gameInput, null);
        init();
    }

    public SlippiGame(ByteBuffer gameInput) {
        input = new SlpReadInput(SlpInputSource.BUFFER, null, gameInput);
        init();
    }

    private void init() {
        statsComputer.registerAll(
                Arrays.asList(actionsComputer, comboComputer, conversionComputer, inputComputer, stockComputer));
        parser = new SlpParser(statsComputer);
    }

    private void process(boolean settingsOnly) throws IOException {
        if (parser.getGameEnd() != null) {
            return;
        }

        SlpFileType slpFile = slpReader.openSlpFile(input);

        EventCallbackFunc callback = (Command command, EventPayloadTypes payload) -> {
            if (payload == null)
                return false;

            switch (command) {
                case FRAME_BOOKEND:
                    parser.handleFrameBookend(command, (FrameBookendType) payload);
                    break;
                case GAME_END:
                    parser.handleGameEnd((GameEndType) payload);
                    break;
                case GAME_START:
                    parser.handleGameStart((GameStartType) payload);
                    break;
                case ITEM_UPDATE:
                    parser.handleItemUpdate(command, (ItemUpdateType) payload);
                    break;
                case POST_FRAME_UPDATE:
                    PostFrameUpdateType postFrameUpdateType = (PostFrameUpdateType) payload;
                    parser.handlePostFrameUpdate(postFrameUpdateType);
                    parser.handleFrameUpdate(command, postFrameUpdateType);
                    break;
                case PRE_FRAME_UPDATE:
                    parser.handleFrameUpdate(command, (PreFrameUpdateType) payload);
                    break;
            }
            return settingsOnly && parser.getSettings() != null;
        };

        readPosition = slpReader.iterateEvents(slpFile, callback, readPosition);
    }

    public GameStartType getSettings() throws IOException {
        process(true);
        return parser.getSettings();
    }

    public FrameEntryType getLatestFrame() throws IOException {
        process(false);
        return parser.getLatestFrame();
    }

    public GameEndType getGameEnd() throws IOException {
        process(false);
        return parser.getGameEnd();
    }

    public FramesType getFrames() throws IOException {
        process(false);
        return parser.getFrames();
    }

    public StatsType getStats() throws IOException {
        if (finalStats != null) {
            return finalStats;
        }
        process(false);

        statsComputer.process();
        List<PlayerInput> inputs = inputComputer.fetch();
        List<StockType> stocks = stockComputer.fetch();
        List<ConversionType> conversions = conversionComputer.fetch();
        List<PlayerIndexedType> indices = StatsQuerier.getSinglesPlayerPermutationsFromSettings(parser.getSettings());
        int playableFrames = parser.getPlayableFrameCount();
        List<OverallType> overall = OverallActions.generateOverallStats(indices, inputs, stocks, conversions,
                playableFrames);

        StatsType statsType = new StatsType(parser.getGameEnd() != null, parser.getLatestFrameNumber(), playableFrames,
                stocks, conversions, comboComputer.fetch(), actionsComputer.fetch(), overall);

        if (parser.getGameEnd() != null) {
            finalStats = statsType;
        }

        return statsType;
    }

    public MetadataType getMetadata() throws IOException {
        if (metadata != null) {
            return metadata;
        }
        SlpFileType slpFile = slpReader.openSlpFile(input);
        metadata = slpReader.getMetadata(slpFile);
        return metadata;
    }

    public String getFilePath() {
        if (input.getSource() != SlpInputSource.FILE) {
            return null;
        }
        return input.getFilePath();
    }
}
