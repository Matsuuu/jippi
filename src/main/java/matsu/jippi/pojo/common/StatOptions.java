package matsu.jippi.pojo.common;

public class StatOptions {
    private boolean processOnTheFly;

    public StatOptions(boolean processOnTheFly) {
        this.processOnTheFly = processOnTheFly;
    }

    public boolean isProcessOnTheFly() {
        return processOnTheFly;
    }

    public void setProcessOnTheFly(boolean processOnTheFly) {
        this.processOnTheFly = processOnTheFly;
    }
}
