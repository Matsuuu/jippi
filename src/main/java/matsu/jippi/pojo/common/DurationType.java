package matsu.jippi.pojo.common;

public class DurationType {

    private int startFrame;
    private Integer endFrame;

    public int getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public Integer getEndFrame() {
        return endFrame;
    }

    public void setEndFrame(Integer endFrame) {
        this.endFrame = endFrame;
    }

    public DurationType(int startFrame, Integer endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }
}
