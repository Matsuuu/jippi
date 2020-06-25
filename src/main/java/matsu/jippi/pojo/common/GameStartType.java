package matsu.jippi.pojo.common;

import java.util.List;

public class GameStartType {
    private String slpVersion;
    private boolean isTeams;
    private boolean isPAL;
    private Integer stageId;
    private List<PlayerType> players;

    public GameStartType() {
    }

    public GameStartType(String slpVersion, boolean isTeams, boolean isPAL, Integer stageId, List<PlayerType> players) {
        this.slpVersion = slpVersion;
        this.isTeams = isTeams;
        this.isPAL = isPAL;
        this.stageId = stageId;
        this.players = players;
    }

    public String getSlpVersion() {
        return slpVersion;
    }

    public void setSlpVersion(String slpVersion) {
        this.slpVersion = slpVersion;
    }

    public boolean isTeams() {
        return isTeams;
    }

    public void setTeams(boolean isTeams) {
        this.isTeams = isTeams;
    }

    public boolean isPAL() {
        return isPAL;
    }

    public void setPAL(boolean isPAL) {
        this.isPAL = isPAL;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public List<PlayerType> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerType> players) {
        this.players = players;
    }

}
