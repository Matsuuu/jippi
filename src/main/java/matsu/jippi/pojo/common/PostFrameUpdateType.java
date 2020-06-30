package matsu.jippi.pojo.common;

import matsu.jippi.interfaces.EventPayloadTypes;

public class PostFrameUpdateType implements EventPayloadTypes {
    private Integer frame;
    private Integer playerIndex;
    private boolean isFollower;
    private Integer internalCharacterId;
    private Integer actionStateId;
    private Float positionX;
    private Float positionY;
    private Integer facingDirection;
    private Integer percent;
    private Float shieldSize;
    private Integer lastAttackLanded;
    private Integer currentComboCount;
    private Integer lastHitBy;
    private Integer stocksRemaining;
    private Integer actionStateCounter;
    private Integer lCancelStatus;

    public PostFrameUpdateType() {
    }

    public PostFrameUpdateType(Integer frame, Integer playerIndex, boolean isFollower, Integer internalCharacterId,
            Integer actionStateId, Float positionX, Float positionY, Integer facingDirection, Integer percent,
            Float shieldSize, Integer lastAttackLanded, Integer currentComboCount, Integer lastHitBy,
            Integer stocksRemaining, Integer actionStateCounter, Integer lCancelStatus) {
        this.frame = frame;
        this.playerIndex = playerIndex;
        this.isFollower = isFollower;
        this.internalCharacterId = internalCharacterId;
        this.actionStateId = actionStateId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.facingDirection = facingDirection;
        this.percent = percent;
        this.shieldSize = shieldSize;
        this.lastAttackLanded = lastAttackLanded;
        this.currentComboCount = currentComboCount;
        this.lastHitBy = lastHitBy;
        this.stocksRemaining = stocksRemaining;
        this.actionStateCounter = actionStateCounter;
        this.lCancelStatus = lCancelStatus;
    }

    public Integer getFrame() {
        return frame;
    }

    public void setFrame(Integer frame) {
        this.frame = frame;
    }

    public Integer getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(Integer playerIndex) {
        this.playerIndex = playerIndex;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean isFollower) {
        this.isFollower = isFollower;
    }

    public Integer getInternalCharacterId() {
        return internalCharacterId;
    }

    public void setInternalCharacterId(Integer internalCharacterId) {
        this.internalCharacterId = internalCharacterId;
    }

    public Integer getActionStateId() {
        return actionStateId;
    }

    public void setActionStateId(Integer actionStateId) {
        this.actionStateId = actionStateId;
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

    public Integer getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Integer facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Integer getPercent() {
        return percent;
    }

    public int getPercentOrZero() {
        return percent == null ? 0 : percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Float getShieldSize() {
        return shieldSize;
    }

    public void setShieldSize(Float shieldSize) {
        this.shieldSize = shieldSize;
    }

    public Integer getLastAttackLanded() {
        return lastAttackLanded;
    }

    public void setLastAttackLanded(Integer lastAttackLanded) {
        this.lastAttackLanded = lastAttackLanded;
    }

    public Integer getCurrentComboCount() {
        return currentComboCount;
    }

    public void setCurrentComboCount(Integer currentComboCount) {
        this.currentComboCount = currentComboCount;
    }

    public Integer getLastHitBy() {
        return lastHitBy;
    }

    public void setLastHitBy(Integer lastHitBy) {
        this.lastHitBy = lastHitBy;
    }

    public Integer getStocksRemaining() {
        return stocksRemaining;
    }

    public void setStocksRemaining(Integer stocksRemaining) {
        this.stocksRemaining = stocksRemaining;
    }

    public Integer getActionStateCounter() {
        return actionStateCounter;
    }

    public void setActionStateCounter(Integer actionStateCounter) {
        this.actionStateCounter = actionStateCounter;
    }

    public Integer getlCancelStatus() {
        return lCancelStatus;
    }

    public void setlCancelStatus(Integer lCancelStatus) {
        this.lCancelStatus = lCancelStatus;
    }
}
