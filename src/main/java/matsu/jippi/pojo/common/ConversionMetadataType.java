package matsu.jippi.pojo.common;

import java.util.HashMap;
import java.util.Map;

public class ConversionMetadataType {
    private Map<Integer, Integer> lastEndFrameByOppIdx = new HashMap<>();

    public ConversionMetadataType() {
    }

    public Map<Integer, Integer> getLastEndFrameByOppIdx() {
        return lastEndFrameByOppIdx;
    }

    public void setLastEndFrameByOppIdx(Map<Integer, Integer> lastEndFrameByOppIdx) {
        this.lastEndFrameByOppIdx = lastEndFrameByOppIdx;
    }
}
