package matsu.jippi.pojo.common;

public class ActionCountsType {
    private PlayerIndexedType playerIndexedType;
    private int wavedashCount;
    private int wavelandCount;
    private int airDodgeCount;
    private int dashDanceCount;
    private int spotDodgeCount;
    private int ledgegrabCount;
    private int rollCount;

    public int getWavedashCount() {
        return wavedashCount;
    }

    public PlayerIndexedType getPlayerIndexedType() {
        return playerIndexedType;
    }

    public void setPlayerIndexedType(PlayerIndexedType playerIndexedType) {
        this.playerIndexedType = playerIndexedType;
    }

    public void setWavedashCount(int wavedashCount) {
        this.wavedashCount = wavedashCount;
    }

    public void incrementWaveDashCount() {
        this.wavedashCount++;
    }

    public int getWavelandCount() {
        return wavelandCount;
    }

    public void setWavelandCount(int wavelandCount) {
        this.wavelandCount = wavelandCount;
    }

    public void incrementWavelandCount() {
        this.wavelandCount++;
    }

    public int getAirDodgeCount() {
        return airDodgeCount;
    }

    public void setAirDodgeCount(int airDodgeCount) {
        this.airDodgeCount = airDodgeCount;
    }

    public void incrementAirDodgeCount() {
        this.airDodgeCount++;
    }

    public void decrementAirDodgeCount() {
        this.airDodgeCount--;
    }

    public int getDashDanceCount() {
        return dashDanceCount;
    }

    public void setDashDanceCount(int dashDanceCount) {
        this.dashDanceCount = dashDanceCount;
    }

    public void incrementDashDanceCount() {
        this.dashDanceCount++;
    }

    public int getSpotDodgeCount() {
        return spotDodgeCount;
    }

    public void setSpotDodgeCount(int spotDodgeCount) {
        this.spotDodgeCount = spotDodgeCount;
    }

    public void incrementSpotDodgeCount() {
        this.spotDodgeCount++;
    }

    public int getLedgegrabCount() {
        return ledgegrabCount;
    }

    public void setLedgegrabCount(int ledgegrabCount) {
        this.ledgegrabCount = ledgegrabCount;
    }

    public void incrementLedgegrabCount() {
        this.ledgegrabCount++;
    }

    public int getRollCount() {
        return rollCount;
    }

    public void setRollCount(int rollCount) {
        this.rollCount = rollCount;
    }

    public void incrementRollCount() {
        this.rollCount++;
    }

    public ActionCountsType(PlayerIndexedType playerIndexedType, int wavedashCount, int wavelandCount,
            int airDodgeCount, int dashDanceCount, int spotDodgeCount, int ledgegrabCount, int rollCount) {
        this.playerIndexedType = playerIndexedType;
        this.wavedashCount = wavedashCount;
        this.wavelandCount = wavelandCount;
        this.airDodgeCount = airDodgeCount;
        this.dashDanceCount = dashDanceCount;
        this.spotDodgeCount = spotDodgeCount;
        this.ledgegrabCount = ledgegrabCount;
        this.rollCount = rollCount;
    }
}
