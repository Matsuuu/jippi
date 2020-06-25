package matsu.jippi.pojo.common;

public class GameEndType {
    private Integer gameEndMethod;
    private Integer lrasInitiatorIndex;

    public GameEndType() {
    }

    public GameEndType(Integer gameEndMethod, Integer lrasInitiatorIndex) {
        this.gameEndMethod = gameEndMethod;
        this.lrasInitiatorIndex = lrasInitiatorIndex;
    }

    public Integer getGameEndMethod() {
        return gameEndMethod;
    }

    public void setGameEndMethod(Integer gameEndMethod) {
        this.gameEndMethod = gameEndMethod;
    }

    public Integer getLrasInitiatorIndex() {
        return lrasInitiatorIndex;
    }

    public void setLrasInitiatorIndex(Integer lrasInitiatorIndex) {
        this.lrasInitiatorIndex = lrasInitiatorIndex;
    }

}
