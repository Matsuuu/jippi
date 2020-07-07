package matsu.jippi.game;

import matsu.jippi.enumeration.stats.Frames;
import matsu.jippi.pojo.common.*;

public class RealtimeDataObject {
    private GameStartType settings;
    private FramesType frames;
    private MetadataType metadata;
    private GameEndType gameEnd;
    private StatsType stats;
    private FrameEntryType latestFrame;

    public RealtimeDataObject() {
    }

    public RealtimeDataObject(GameStartType settings, FramesType frames, MetadataType metadata, GameEndType gameEnd, StatsType stats, FrameEntryType latestFrame) {
        this.settings = settings;
        this.frames = frames;
        this.metadata = metadata;
        this.gameEnd = gameEnd;
        this.stats = stats;
        this.latestFrame = latestFrame;
    }

    public GameStartType getSettings() {
        return settings;
    }

    public void setSettings(GameStartType settings) {
        this.settings = settings;
    }

    public FramesType getFrames() {
        return frames;
    }

    public void setFrames(FramesType frames) {
        this.frames = frames;
    }

    public MetadataType getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataType metadata) {
        this.metadata = metadata;
    }

    public GameEndType getGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(GameEndType gameEnd) {
        this.gameEnd = gameEnd;
    }

    public StatsType getStats() {
        return stats;
    }

    public void setStats(StatsType stats) {
        this.stats = stats;
    }

    public FrameEntryType getLatestFrame() {
        return latestFrame;
    }

    public void setLatestFrame(FrameEntryType latestFrame) {
        this.latestFrame = latestFrame;
    }
}
