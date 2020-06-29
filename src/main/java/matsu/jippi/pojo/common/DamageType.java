package matsu.jippi.pojo.common;

public class DamageType {
    private int startPercent;
    private int currentPercent;
    private Integer endPercent;

    public int getStartPercent() {
        return startPercent;
    }

    public void setStartPercent(int startPercent) {
        this.startPercent = startPercent;
    }

    public int getCurrentPercent() {
        return currentPercent;
    }

    public void setCurrentPercent(int currentPercent) {
        this.currentPercent = currentPercent;
    }

    public Integer getEndPercent() {
        return endPercent;
    }

    public void setEndPercent(Integer endPercent) {
        this.endPercent = endPercent;
    }

    public DamageType(int startPercent, int currentPercent, Integer endPercent) {
        this.startPercent = startPercent;
        this.currentPercent = currentPercent;
        this.endPercent = endPercent;
    }
}
