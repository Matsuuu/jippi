package matsu.jippi.enumeration.stats;

public enum Timers {
    PUNISH_RESET_FRAMES(45), RECOVERY_RESET_FRAMES(45), COMBO_STRING_RESET_FRAMES(45);

    private int frames;

    public int getFrames() {
        return frames;
    }

    private Timers(int frames) {
        this.frames = frames;
    }

}
