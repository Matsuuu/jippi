package matsu.jippi.pojo.common;

public class PlayerIndexedType {
    private int playerIndex;
    private int opponentIndex;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getOpponentIndex() {
        return opponentIndex;
    }

    public void setOpponentIndex(int opponentIndex) {
        this.opponentIndex = opponentIndex;
    }

    public PlayerIndexedType(int playerIndex, int opponentIndex) {
        this.playerIndex = playerIndex;
        this.opponentIndex = opponentIndex;
    }
}
