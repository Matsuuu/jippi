package matsu.jippi.pojo.common;

import matsu.jippi.interfaces.EventPayloadTypes;

public class PreFrameUpdateType implements EventPayloadTypes {
    private Integer frame;
    private Integer playerIndex;
    private boolean isFollower;
    private Integer seed;
    private Integer actionStateId;
    private Integer positionX;
    private Integer positionY;
    private Integer facingDirection;
    private Integer joystickX;
    private Integer joystickY;
    private Integer cStickX;
    private Integer cStickY;
    private Integer trigger;
    private Integer buttons;
    private Integer physicalButtons;
    private Integer physicalLTrigger;
    private Integer physicalRTrigger;
    private Integer percent;

    public PreFrameUpdateType() {
    }

    public PreFrameUpdateType(Integer frame, Integer playerIndex, boolean isFollower, Integer seed,
            Integer actionStateId, Integer positionX, Integer positionY, Integer facingDirection, Integer joystickX,
            Integer joystickY, Integer cStickX, Integer cStickY, Integer trigger, Integer buttons,
            Integer physicalButtons, Integer physicalLTrigger, Integer physicalRTrigger, Integer percent) {
        this.frame = frame;
        this.playerIndex = playerIndex;
        this.isFollower = isFollower;
        this.seed = seed;
        this.actionStateId = actionStateId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.facingDirection = facingDirection;
        this.joystickX = joystickX;
        this.joystickY = joystickY;
        this.cStickX = cStickX;
        this.cStickY = cStickY;
        this.trigger = trigger;
        this.buttons = buttons;
        this.physicalButtons = physicalButtons;
        this.physicalLTrigger = physicalLTrigger;
        this.physicalRTrigger = physicalRTrigger;
        this.percent = percent;
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

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Integer getActionStateId() {
        return actionStateId;
    }

    public void setActionStateId(Integer actionStateId) {
        this.actionStateId = actionStateId;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Integer facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Integer getJoystickX() {
        return joystickX;
    }

    public void setJoystickX(Integer joystickX) {
        this.joystickX = joystickX;
    }

    public Integer getJoystickY() {
        return joystickY;
    }

    public void setJoystickY(Integer joystickY) {
        this.joystickY = joystickY;
    }

    public Integer getcStickX() {
        return cStickX;
    }

    public void setcStickX(Integer cStickX) {
        this.cStickX = cStickX;
    }

    public Integer getcStickY() {
        return cStickY;
    }

    public void setcStickY(Integer cStickY) {
        this.cStickY = cStickY;
    }

    public Integer getTrigger() {
        return trigger;
    }

    public void setTrigger(Integer trigger) {
        this.trigger = trigger;
    }

    public Integer getButtons() {
        return buttons;
    }

    public void setButtons(Integer buttons) {
        this.buttons = buttons;
    }

    public Integer getPhysicalButtons() {
        return physicalButtons;
    }

    public void setPhysicalButtons(Integer physicalButtons) {
        this.physicalButtons = physicalButtons;
    }

    public Integer getPhysicalLTrigger() {
        return physicalLTrigger;
    }

    public void setPhysicalLTrigger(Integer physicalLTrigger) {
        this.physicalLTrigger = physicalLTrigger;
    }

    public Integer getPhysicalRTrigger() {
        return physicalRTrigger;
    }

    public void setPhysicalRTrigger(Integer physicalRTrigger) {
        this.physicalRTrigger = physicalRTrigger;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
