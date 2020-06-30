package matsu.jippi.pojo.common;

import java.util.Map;

public class FramesType {
    private Map<Integer, FrameEntryType> frames;

    public FramesType() {
    }

    public Map<Integer, FrameEntryType> getFrames() {
        return frames;
    }

    public void setFrames(Map<Integer, FrameEntryType> frames) {
        this.frames = frames;
    }
}
