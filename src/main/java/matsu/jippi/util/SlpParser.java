package matsu.jippi.util;

import java.awt.*;
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
import matsu.jippi.pojo.common.FrameUpdateType;
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

    public Integer getLatestFrameNumber() {
        return latestFrameIndex;
    }

    public int getPlayableFrameCount() {
        if (latestFrameIndex == null)
            return 0;
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

        if (semVerGreaterThan(payload.getSlpVersion(), "1.6.0")) {
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

    private FrameEntryPlayerOrFollower getFrameEntryPlayerOrFollower(FrameEntryType frameEntry,
            FrameUpdateType payload) {
        if (frameEntry == null) {
            return new FrameEntryPlayerOrFollower();
        }
        if (payload.isFollower()) {
            if (frameEntry.getFollowers().containsKey(payload.getPlayerIndex())) {
                return frameEntry.getFollowers().get(payload.getPlayerIndex());
            }
        } else {
            if (frameEntry.getPlayers().containsKey(payload.getPlayerIndex())) {
                return frameEntry.getPlayers().get(payload.getPlayerIndex());
            }
        }
        return new FrameEntryPlayerOrFollower();
    }

    public void handleFrameUpdate(Command command, FrameUpdateType payload) {
        latestFrameIndex = payload.getFrame();

        // Initialize frame and pof
        FrameEntryType frameEntry = !frames.getFrames().containsKey(payload.getFrame()) ? new FrameEntryType()
                : frames.getFrames().get(payload.getFrame());
        FrameEntryPlayerOrFollower pof = getFrameEntryPlayerOrFollower(frameEntry, payload);

        if (command == Command.PRE_FRAME_UPDATE) {
            pof.setPre((PreFrameUpdateType) payload);
        } else {
            pof.setPost((PostFrameUpdateType) payload);
        }

        // Set accordingly
        if (payload.isFollower()) {
            frameEntry.getFollowers().put(payload.getPlayerIndex(), pof);
        } else {
            frameEntry.getPlayers().put(payload.getPlayerIndex(), pof);
        }
        // Set frame data
        frameEntry.setFrame(payload.getFrame());
        // Append to frames
        frames.getFrames().put(payload.getFrame(), frameEntry);

        GameStartType settings = getSettings();
        if (settings == null || semVerLessThan(settings.getSlpVersion(), "2.2.0")) {
            statsComputer.addFrame(frames.getFrames().get(payload.getFrame()));
        } else {
            frames.getFrames().get(payload.getFrame()).setTransferComplete(false);
        }
    }

    private boolean semVerLessThan(String version, String target) {
        if (version.equals(target))
            return true;

        String[] versionNumbers = version.split("\\.");
        String[] targetNumbers = target.split("\\.");

        int versionMajor = Integer.parseInt(versionNumbers[0]);
        int versionMinor = Integer.parseInt(versionNumbers[1]);
        int versionPatch = Integer.parseInt(versionNumbers[2]);

        int targetMajor = Integer.parseInt(targetNumbers[0]);
        int targetMinor = Integer.parseInt(targetNumbers[1]);
        int targetPatch = Integer.parseInt(targetNumbers[2]);

        if (versionMajor < targetMajor)
            return true;
        if (versionMajor == targetMajor && versionMinor < targetMinor)
            return true;
        if (versionMajor == targetMajor && versionMinor == targetMinor && versionPatch <= targetPatch)
            return true;

        return false;
    }

    private boolean semVerGreaterThan(String version, String target) {
        if (version.equals(target))
            return true;

        String[] versionNumbers = version.split("\\.");
        String[] targetNumbers = target.split("\\.");

        int versionMajor = Integer.parseInt(versionNumbers[0]);
        int versionMinor = Integer.parseInt(versionNumbers[1]);
        int versionPatch = Integer.parseInt(versionNumbers[2]);

        int targetMajor = Integer.parseInt(targetNumbers[0]);
        int targetMinor = Integer.parseInt(targetNumbers[1]);
        int targetPatch = Integer.parseInt(targetNumbers[2]);

        if (versionMajor > targetMajor)
            return true;
        if (versionMajor == targetMajor && versionMinor > targetMinor)
            return true;
        if (versionMajor == targetMajor && versionMinor == targetMinor && versionPatch >= targetPatch)
            return true;

        return false;
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
