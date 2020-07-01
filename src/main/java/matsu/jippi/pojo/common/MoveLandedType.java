package matsu.jippi.pojo.common;

public class MoveLandedType {
    private int frame;
    private int moveId;
    private int hitCount;
    private float damage;

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

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public MoveLandedType(int frame, int moveId, int hitCount, float damage) {
        this.frame = frame;
        this.moveId = moveId;
        this.hitCount = hitCount;
        this.damage = damage;
    }

}
