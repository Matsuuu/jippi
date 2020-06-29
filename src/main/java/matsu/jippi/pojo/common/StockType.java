package matsu.jippi.pojo.common;

public class StockType {
    private PlayerIndexedType playerIndexedType;
    private DurationType durationType;
    private DamageType damageType;
    private int count;
    private Integer deathAnimation;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getDeathAnimation() {
        return deathAnimation;
    }

    public void setDeathAnimation(Integer deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    public StockType() {
    }

    public StockType(PlayerIndexedType playerIndexedType, DurationType durationType, DamageType damageType, int count,
            Integer deathAnimation) {
        this.playerIndexedType = playerIndexedType;
        this.durationType = durationType;
        this.damageType = damageType;
        this.count = count;
        this.deathAnimation = deathAnimation;
    }
}
