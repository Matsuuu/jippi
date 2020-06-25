package matsu.jippi.pojo.common;

public class PlayerType {
    private int playerIndex;
    private int port;
    private Integer characterId;
    private Integer characterColor;
    private Integer startStocks;
    private Integer type;
    private Integer teamId;
    private String controllerFix;
    private String nameTag;

    public PlayerType() {
    }

    public PlayerType(int playerIndex, int port, Integer characterId, Integer characterColor, Integer startStocks,
            Integer type, Integer teamId, String controllerFix, String nameTag) {
        this.playerIndex = playerIndex;
        this.port = port;
        this.characterId = characterId;
        this.characterColor = characterColor;
        this.startStocks = startStocks;
        this.type = type;
        this.teamId = teamId;
        this.controllerFix = controllerFix;
        this.nameTag = nameTag;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public Integer getCharacterColor() {
        return characterColor;
    }

    public void setCharacterColor(Integer characterColor) {
        this.characterColor = characterColor;
    }

    public Integer getStartStocks() {
        return startStocks;
    }

    public void setStartStocks(Integer startStocks) {
        this.startStocks = startStocks;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getControllerFix() {
        return controllerFix;
    }

    public void setControllerFix(String controllerFix) {
        this.controllerFix = controllerFix;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
