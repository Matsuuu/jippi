package matsu.jippi.pojo.common;

public class ItemUpdateType {
    private Integer frame;
    private Integer typeId;
    private Integer state;
    private Integer facingDirection;
    private Float velocityX;
    private Float velocityY;
    private Float positionX;
    private Float positionY;
    private Float damageTaken;
    private Float expirationTimer;
    private Integer spawnId;

    public ItemUpdateType() {
    }

    public ItemUpdateType(Integer frame, Integer typeId, Integer state, Integer facingDirection, Float velocityX,
            Float velocityY, Float positionX, Float positionY, Float damageTaken, Float expirationTimer,
            Integer spawnId) {
        this.frame = frame;
        this.typeId = typeId;
        this.state = state;
        this.facingDirection = facingDirection;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.damageTaken = damageTaken;
        this.expirationTimer = expirationTimer;
        this.spawnId = spawnId;
    }

    public Integer getFrame() {
        return frame;
    }

    public void setFrame(Integer frame) {
        this.frame = frame;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Integer facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(Float velocityX) {
        this.velocityX = velocityX;
    }

    public Float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(Float velocityY) {
        this.velocityY = velocityY;
    }

    public Float getPositionX() {
        return positionX;
    }

    public void setPositionX(Float positionX) {
        this.positionX = positionX;
    }

    public Float getPositionY() {
        return positionY;
    }

    public void setPositionY(Float positionY) {
        this.positionY = positionY;
    }

    public Float getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(Float damageTaken) {
        this.damageTaken = damageTaken;
    }

    public Float getExpirationTimer() {
        return expirationTimer;
    }

    public void setExpirationTimer(Float expirationTimer) {
        this.expirationTimer = expirationTimer;
    }

    public Integer getSpawnId() {
        return spawnId;
    }

    public void setSpawnId(Integer spawnId) {
        this.spawnId = spawnId;
    }

}
