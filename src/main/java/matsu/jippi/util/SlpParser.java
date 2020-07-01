package matsu.jippi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.slpreader.Command;
import matsu.jippi.enumeration.stats.Frames;
import matsu.jippi.interfaces.EventPayloadTypes;
import matsu.jippi.pojo.common.FrameBookendType;
import matsu.jippi.pojo.common.FrameEntryPlayerOrFollower;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.GameEndType;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.ItemUpdateType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PlayerType;
import matsu.jippi.pojo.common.PostFrameUpdateType;
import matsu.jippi.pojo.common.PreFrameUpdateType;
import matsu.jippi.stats.Stats;
import matsu.jippi.stats.StatsQuerier;

public class SlpParser {
    private Stats statsComputer;
    private FramesType frames = new FramesType();
    private GameStartType settings;
    private GameEndType gameEnd;
    private Integer latestFrameIndex;
    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private boolean settingsComplete = false;

    public SlpParser(Stats statsComputer) {
        this.statsComputer = statsComputer;
    }

    public int getLatestFrameNumber() {
        return latestFrameIndex;
    }

    public int getPlayableFrameCount() {
        return latestFrameIndex < Frames.FIRST_PLAYABLE.getFrame() ? 0
                : latestFrameIndex - Frames.FIRST_PLAYABLE.getFrame();
    }

    public FrameEntryType getLatestFrame() {
        FramesType allFrames = getFrames();
        int frameIndex = latestFrameIndex != null ? latestFrameIndex : Frames.FIRST.getFrame();
        int indexToUse = gameEnd != null ? frameIndex : frameIndex - 1;
        return allFrames.getFrames().containsKey(indexToUse) ? allFrames.getFrames().get(indexToUse) : null;
    }

    public GameStartType getSettings() {
        return settingsComplete ? settings : null;
    }

    public GameEndType getGameEnd() {
        return gameEnd;
    }

    public FramesType getFrames() {
        return frames;
    }

    public void handleGameEnd(GameEndType payload) {
        this.gameEnd = payload;
    }

    public void handleGameStart(GameStartType payload) {
        settings = payload;
        List<PlayerType> players = payload.getPlayers();
        settings.setPlayers(players.stream().filter(p -> p.getType() != 3).collect(Collectors.toList()));
        playerPermutations = StatsQuerier.getSinglesPlayerPermutationsFromSettings(settings);
        statsComputer.setPlayerPermutations(playerPermutations);

        if (payload.getSlpVersion().equals("1.6.0")) {
            settingsComplete = true;
        }
    }

    public void handlePostFrameUpdate(PostFrameUpdateType payload) {
        if (settingsComplete) {
            return;
        }

        if (payload.getFrame() <= Frames.FIRST.getFrame()) {
            int playerIndex = payload.getPlayerIndex();
            Map<Integer, PlayerType> playersByIndex = new HashMap<>();
            settings.getPlayers().forEach(play -> playersByIndex.put(play.getPlayerIndex(), play));

            switch (payload.getInternalCharacterId()) {
                case 0x7:
                    playersByIndex.get(playerIndex).setCharacterId(0x13);
                    break;
                case 0x13:
                    playersByIndex.get(playerIndex).setCharacterId(0x12);
                    break;
            }
        }
        settingsComplete = payload.getFrame() > Frames.FIRST.getFrame();
    }

    public void handleFrameUpdate(Command command, PreFrameUpdateType payload) {
        String field = payload.isFollower() ? "followers" : "players";
        latestFrameIndex = payload.getFrame();
        if (!frames.getFrames().containsKey(payload.getFrame())) {
            frames.getFrames().put(payload.getFrame(), new FrameEntryType());
        }

        FrameEntryPlayerOrFollower playerOrFollower;
        if (field.equals("players")) {
            playerOrFollower = frames.getFrames().get(payload.getFrame()).getPlayers().get(payload.getPlayerIndex());
        } else {
            playerOrFollower = frames.getFrames().get(payload.getFrame()).getFollowers().get(payload.getPlayerIndex());
        }
        if (playerOrFollower == null) {
            playerOrFollower = new FrameEntryPlayerOrFollower();
        }
        playerOrFollower.setPre(payload);

        GameStartType settings = getSettings();
        if (settings == null || settings.getSlpVersion().equals("2.2.0")) {
            statsComputer.addFrame(frames.getFrames().get(payload.getFrame()));
        } else {
            frames.getFrames().put(payload.getFrame(), new FrameEntryType());
        }
    }

    public void handleFrameUpdate(Command command, PostFrameUpdateType payload) {
        String field = payload.isFollower() ? "followers" : "players";
        latestFrameIndex = payload.getFrame();
        if (!frames.getFrames().containsKey(payload.getFrame())) {
            frames.getFrames().put(payload.getFrame(), new FrameEntryType());
        }

        FrameEntryPlayerOrFollower playerOrFollower;
        if (field.equals("players")) {
            playerOrFollower = frames.getFrames().get(payload.getFrame()).getPlayers().get(payload.getPlayerIndex());
        } else {
            playerOrFollower = frames.getFrames().get(payload.getFrame()).getFollowers().get(payload.getPlayerIndex());
        }
        if (playerOrFollower == null) {
            playerOrFollower = new FrameEntryPlayerOrFollower();
        }
        playerOrFollower.setPost(payload);

        GameStartType settings = getSettings();
        if (settings == null || settings.getSlpVersion().equals("2.2.0")) {
            statsComputer.addFrame(frames.getFrames().get(payload.getFrame()));
        } else {
            frames.getFrames().put(payload.getFrame(), new FrameEntryType());
        }
    }

    public void handleItemUpdate(Command command, ItemUpdateType payload) {
        List<ItemUpdateType> items = frames.getFrames().get(payload.getFrame()).getItems();
        items.add(payload);

        frames.getFrames().get(payload.getFrame()).setItems(items);
    }

    public void handleFrameBookend(Command command, FrameBookendType payload) {
        statsComputer.addFrame(frames.getFrames().get(payload.getFrame()));
    }

}
