package matsu.jippi.pojo.common;

import matsu.jippi.interfaces.EventPayloadTypes;

public class ItemUpdateType implements EventPayloadTypes {
    private Integer frame;
    private Integer typeId;
    private Integer state;
    private Float facingDirection;
    private Float velocityX;
    private Float velocityY;
    private Float positionX;
    private Float positionY;
    private Integer damageTaken;
    private Integer expirationTimer;
    private Integer spawnId;

    public ItemUpdateType() {
    }

    public ItemUpdateType(Integer frame, Integer typeId, Integer state, Float facingDirection, Float velocityX,
            Float velocityY, Float positionX, Float positionY, Integer damageTaken, Integer expirationTimer,
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

    public Float getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Float facingDirection) {
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

    public Integer getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(Integer damageTaken) {
        this.damageTaken = damageTaken;
    }

    public Integer getExpirationTimer() {
        return expirationTimer;
    }

    public void setExpirationTimer(Integer expirationTimer) {
        this.expirationTimer = expirationTimer;
    }

    public Integer getSpawnId() {
        return spawnId;
    }

    public void setSpawnId(Integer spawnId) {
        this.spawnId = spawnId;
    }
}
