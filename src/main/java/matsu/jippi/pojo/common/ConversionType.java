package matsu.jippi.pojo.common;

import java.util.List;

public class ConversionType {
    private PlayerIndexedType playerIndexedType;
    private DurationType durationType;
    private DamageType damageType;
    private List<MoveLandedType> moves;
    private String openingType;
    private boolean didKill;

    public List<MoveLandedType> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveLandedType> moves) {
        this.moves = moves;
    }

    public String getOpeningType() {
        return openingType;
    }

    public void setOpeningType(String openingType) {
        this.openingType = openingType;
    }

    public boolean isDidKill() {
        return didKill;
    }

    public void setDidKill(boolean didKill) {
        this.didKill = didKill;
    }

    public PlayerIndexedType getPlayerIndexedType() {
        return playerIndexedType;
    }

    public void setPlayerIndexedType(PlayerIndexedType playerIndexedType) {
        this.playerIndexedType = playerIndexedType;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public ConversionType(PlayerIndexedType playerIndexedType, DurationType durationType, DamageType damageType,
            List<MoveLandedType> moves, String openingType, boolean didKill) {
        this.playerIndexedType = playerIndexedType;
        this.durationType = durationType;
        this.damageType = damageType;
        this.moves = moves;
        this.openingType = openingType;
        this.didKill = didKill;
    }
}
