package matsu.jippi.pojo.common;

public class PlayerConversionState {
    private ConversionType conversion;
    private MoveLandedType move;
    private int resetCounter;
    private Integer lastHitAnimation;

    public PlayerConversionState(ConversionType conversion, MoveLandedType move, int resetCounter,
            Integer lastHitAnimation) {
        this.conversion = conversion;
        this.move = move;
        this.resetCounter = resetCounter;
        this.lastHitAnimation = lastHitAnimation;
    }

    public ConversionType getConversion() {
        return conversion;
    }

    public void setConversion(ConversionType conversion) {
        this.conversion = conversion;
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
}
