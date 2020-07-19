package matsu.jippi.pojo.common;

public class OverallType {
    private PlayerIndexedType playerIndexedType;
    private int inputCount;
    private int conversionCount;
    private float totalDamage;
    private int killCount;
    private RatioType successfulConversion;
    private RatioType inputsPerMinute;
    private RatioType openingsPerKill;
    private RatioType damagePerOpening;
    private RatioType neutralWinRatio;
    private RatioType counterHitRatio;
    private RatioType beneficialTradeRatio;

    public PlayerIndexedType getPlayerIndexedType() {
        return playerIndexedType;
    }

    public void setPlayerIndexedType(PlayerIndexedType playerIndexedType) {
        this.playerIndexedType = playerIndexedType;
    }

    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public int getConversionCount() {
        return conversionCount;
    }

    public void setConversionCount(int conversionCount) {
        this.conversionCount = conversionCount;
    }

    public float getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(float totalDamage) {
        this.totalDamage = totalDamage;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public RatioType getSuccessfulConversion() {
        return successfulConversion;
    }

    public void setSuccessfulConversion(RatioType successfulConversion) {
        this.successfulConversion = successfulConversion;
    }

    public RatioType getInputsPerMinute() {
        return inputsPerMinute;
    }

    public void setInputsPerMinute(RatioType inputsPerMinute) {
        this.inputsPerMinute = inputsPerMinute;
    }

    public RatioType getOpeningsPerKill() {
        return openingsPerKill;
    }

    public void setOpeningsPerKill(RatioType openingsPerKill) {
        this.openingsPerKill = openingsPerKill;
    }

    public RatioType getDamagePerOpening() {
        return damagePerOpening;
    }

    public void setDamagePerOpening(RatioType damagePerOpening) {
        this.damagePerOpening = damagePerOpening;
    }

    public RatioType getNeutralWinRatio() {
        return neutralWinRatio;
    }

    public void setNeutralWinRatio(RatioType neutralWinRatio) {
        this.neutralWinRatio = neutralWinRatio;
    }

    public RatioType getCounterHitRatio() {
        return counterHitRatio;
    }

    public void setCounterHitRatio(RatioType counterHitRatio) {
        this.counterHitRatio = counterHitRatio;
    }

    public RatioType getBeneficialTradeRatio() {
        return beneficialTradeRatio;
    }

    public void setBeneficialTradeRatio(RatioType beneficialTradeRatio) {
        this.beneficialTradeRatio = beneficialTradeRatio;
    }

    public OverallType(PlayerIndexedType playerIndexedType, int inputCount, int conversionCount, float totalDamage,
            int killCount, RatioType successfulConversion, RatioType inputsPerMinute, RatioType openingsPerKill,
            RatioType damagePerOpening, RatioType neutralWinRatio, RatioType counterHitRatio,
            RatioType beneficialTradeRatio) {
        this.playerIndexedType = playerIndexedType;
        this.inputCount = inputCount;
        this.conversionCount = conversionCount;
        this.totalDamage = totalDamage;
        this.killCount = killCount;
        this.successfulConversion = successfulConversion;
        this.inputsPerMinute = inputsPerMinute;
        this.openingsPerKill = openingsPerKill;
        this.damagePerOpening = damagePerOpening;
        this.neutralWinRatio = neutralWinRatio;
        this.counterHitRatio = counterHitRatio;
        this.beneficialTradeRatio = beneficialTradeRatio;
    }
}
