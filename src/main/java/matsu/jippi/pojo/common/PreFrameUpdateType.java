package matsu.jippi.pojo.common;

import matsu.jippi.interfaces.EventPayloadTypes;

public class PreFrameUpdateType implements EventPayloadTypes {
    private Integer frame;
    private Integer playerIndex;
    private boolean isFollower;
    private Integer seed;
    private Integer actionStateId;
    private Float positionX;
    private Float positionY;
    private Float facingDirection;
    private Float joystickX;
    private Float joystickY;
    private Float cStickX;
    private Float cStickY;
    private Float trigger;
    private Integer buttons;
    private Integer physicalButtons;
    private Float physicalLTrigger;
    private Float physicalRTrigger;
    private Float percent;

    public PreFrameUpdateType() {
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

    public Float getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Float facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Float getJoystickX() {
        return joystickX;
    }

    public void setJoystickX(Float joystickX) {
        this.joystickX = joystickX;
    }

    public Float getJoystickY() {
        return joystickY;
    }

    public void setJoystickY(Float joystickY) {
        this.joystickY = joystickY;
    }

    public Float getcStickX() {
        return cStickX;
    }

    public void setcStickX(Float cStickX) {
        this.cStickX = cStickX;
    }

    public Float getcStickY() {
        return cStickY;
    }

    public void setcStickY(Float cStickY) {
        this.cStickY = cStickY;
    }

    public Float getTrigger() {
        return trigger;
    }

    public void setTrigger(Float trigger) {
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

    public Float getPhysicalLTrigger() {
        return physicalLTrigger;
    }

    public void setPhysicalLTrigger(Float physicalLTrigger) {
        this.physicalLTrigger = physicalLTrigger;
    }

    public Float getPhysicalRTrigger() {
        return physicalRTrigger;
    }

    public void setPhysicalRTrigger(Float physicalRTrigger) {
        this.physicalRTrigger = physicalRTrigger;
    }

    public Float getPercent() {
        return percent;
    }

    public Float getPercentOrZero() {
        return percent == null ? 0 : percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public PreFrameUpdateType(Integer frame, Integer playerIndex, boolean isFollower, Integer seed,
            Integer actionStateId, Float positionX, Float positionY, Float facingDirection, Float joystickX,
            Float joystickY, Float cStickX, Float cStickY, Float trigger, Integer buttons, Integer physicalButtons,
            Float physicalLTrigger, Float physicalRTrigger, Float percent) {
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

}
