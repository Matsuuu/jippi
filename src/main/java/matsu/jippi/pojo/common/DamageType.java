package matsu.jippi.pojo.common;

public class DamageType {
    private float startPercent;
    private float currentPercent;
    private Float endPercent;

    public DamageType(float startPercent, float currentPercent, Float endPercent) {
        this.startPercent = startPercent;
        this.currentPercent = currentPercent;
        this.endPercent = endPercent;
    }

    public float getStartPercent() {
        return startPercent;
    }

    public void setStartPercent(float startPercent) {
        this.startPercent = startPercent;
    }

    public float getCurrentPercent() {
        return currentPercent;
    }

    public void setCurrentPercent(float currentPercent) {
        this.currentPercent = currentPercent;
    }

    public Float getEndPercent() {
        return endPercent;
    }

    public void setEndPercent(Float endPercent) {
        this.endPercent = endPercent;
    }

}
