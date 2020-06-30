package matsu.jippi.pojo.common;

public class PlayerInput {
    private int playerIndex;
    private int opponentIndex;
    private int inputCount;

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

    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public PlayerInput(int playerIndex, int opponentIndex, int inputCount) {
        this.playerIndex = playerIndex;
        this.opponentIndex = opponentIndex;
        this.inputCount = inputCount;
    }

}
