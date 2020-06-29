package matsu.jippi.pojo.common;

import java.util.List;

public class ComboType {
    private PlayerIndexedType playerIndexedType;
    private DurationType durationType;
    private DamageType damageType;
    private List<MoveLandedType> moves;
    private boolean didKill;

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

    public List<MoveLandedType> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveLandedType> moves) {
        this.moves = moves;
    }

    public boolean isDidKill() {
        return didKill;
    }

    public void setDidKill(boolean didKill) {
        this.didKill = didKill;
    }

    public ComboType(PlayerIndexedType playerIndexedType, DurationType durationType, DamageType damageType,
            List<MoveLandedType> moves, boolean didKill) {
        this.playerIndexedType = playerIndexedType;
        this.durationType = durationType;
        this.damageType = damageType;
        this.moves = moves;
        this.didKill = didKill;
    }
}
