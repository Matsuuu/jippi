package matsu.jippi.pojo.common;

import java.util.List;

public class StatsType {
    private boolean gameComplete;
    private Integer lastFrame;
    private Integer playableFrameCount;
    private List<StockType> stocks;
    private List<ConversionType> conversions;
    private List<ComboType> combos;
    private List<ActionCountsType> actionCounts;
    private List<OverallType> overall;

    public StatsType(boolean gameComplete, Integer lastFrame, Integer playableFrameCount, List<StockType> stocks,
            List<ConversionType> conversions, List<ComboType> combos, List<ActionCountsType> actionCounts,
            List<OverallType> overall) {
        this.gameComplete = gameComplete;
        this.lastFrame = lastFrame;
        this.playableFrameCount = playableFrameCount;
        this.stocks = stocks;
        this.conversions = conversions;
        this.combos = combos;
        this.actionCounts = actionCounts;
        this.overall = overall;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    public Integer getLastFrame() {
        return lastFrame;
    }

    public void setLastFrame(Integer lastFrame) {
        this.lastFrame = lastFrame;
    }

    public Integer getPlayableFrameCount() {
        return playableFrameCount;
    }

    public void setPlayableFrameCount(Integer playableFrameCount) {
        this.playableFrameCount = playableFrameCount;
    }

    public List<StockType> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockType> stocks) {
        this.stocks = stocks;
    }

    public List<ConversionType> getConversions() {
        return conversions;
    }

    public void setConversions(List<ConversionType> conversions) {
        this.conversions = conversions;
    }

    public List<ComboType> getCombos() {
        return combos;
    }

    public void setCombos(List<ComboType> combos) {
        this.combos = combos;
    }

    public List<ActionCountsType> getActionCounts() {
        return actionCounts;
    }

    public void setActionCounts(List<ActionCountsType> actionCounts) {
        this.actionCounts = actionCounts;
    }

    public List<OverallType> getOverall() {
        return overall;
    }

    public void setOverall(List<OverallType> overall) {
        this.overall = overall;
    }
}
