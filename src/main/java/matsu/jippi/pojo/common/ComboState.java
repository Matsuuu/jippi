package matsu.jippi.pojo.common;

public class ComboState {
    private ComboType combo;
    private MoveLandedType move;
    private int resetCounter;
    private Integer lastHitAnimation;

    public ComboType getCombo() {
        return combo;
    }

    public void setCombo(ComboType combo) {
        this.combo = combo;
    }

    public MoveLandedType getMove() {
        return move;
    }

    public void setMove(MoveLandedType move) {
        this.move = move;
    }

    public int getResetCounter() {
        return resetCounter;
    }

    public void setResetCounter(int resetCounter) {
        this.resetCounter = resetCounter;
    }

    public Integer getLastHitAnimation() {
        return lastHitAnimation;
    }

    public void setLastHitAnimation(Integer lastHitAnimation) {
        this.lastHitAnimation = lastHitAnimation;
    }

    public ComboState(ComboType combo, MoveLandedType move, int resetCounter, Integer lastHitAnimation) {
        this.combo = combo;
        this.move = move;
        this.resetCounter = resetCounter;
        this.lastHitAnimation = lastHitAnimation;
    }

}
