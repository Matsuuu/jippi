package matsu.jippi.pojo.common;

public class RatioType {
    private int count;
    private int total;
    private Integer ratio;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public RatioType(int count, int total, Integer ratio) {
        this.count = count;
        this.total = total;
        this.ratio = ratio;
    }

}
