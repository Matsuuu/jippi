package matsu.jippi.pojo.common;

import java.util.Map;

public class FrameType {
    private Map<Integer, FrameEntryType> type;

    public Map<Integer, FrameEntryType> getType() {
        return type;
    }

    public void setType(Map<Integer, FrameEntryType> type) {
        this.type = type;
    }

    public FrameType(Map<Integer, FrameEntryType> type) {
        this.type = type;
    }
}
