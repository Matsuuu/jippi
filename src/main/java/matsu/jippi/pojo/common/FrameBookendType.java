package matsu.jippi.pojo.common;

import matsu.jippi.interfaces.EventPayloadTypes;

public class FrameBookendType implements EventPayloadTypes {
    private Integer frame;

    public FrameBookendType(Integer frame) {
        this.frame = frame;
    }

    public Integer getFrame() {
        return frame;
    }

    public void setFrame(Integer frame) {
        this.frame = frame;
    }

}
