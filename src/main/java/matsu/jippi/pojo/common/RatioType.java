package matsu.jippi.pojo.common;

public class RatioType {
    private float count;
    private float total;
    private Float ratio;

    public RatioType(float count, float total, Float ratio) {
        this.count = count;
        this.total = total;
        this.ratio = ratio;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }
}
