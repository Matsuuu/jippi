package matsu.jippi.pojo.common;

public class MoveLandedType {
    private int frame;
    private int moveId;
    private int hitCount;
    private int damage;

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public MoveLandedType(int frame, int moveId, int hitCount, int damage) {
        this.frame = frame;
        this.moveId = moveId;
        this.hitCount = hitCount;
        this.damage = damage;
    }
}
