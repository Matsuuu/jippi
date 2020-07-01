package matsu.jippi.pojo.common;

import java.util.HashMap;
import java.util.Map;

public class FramesType {
    private Map<Integer, FrameEntryType> frames = new HashMap<>();

    public FramesType() {
    }

    public Map<Integer, FrameEntryType> getFrames() {
        return frames;
    }

    public void setFrames(Map<Integer, FrameEntryType> frames) {
        this.frames = frames;
    }
}
