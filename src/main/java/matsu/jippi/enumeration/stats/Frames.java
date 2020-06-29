package matsu.jippi.enumeration.stats;

public enum Frames {
    FIRST(-123), FIRST_PLAYABLE(-39);

    private int frame;

    public int getFrame() {
        return frame;
    }

    private Frames(int frame) {
        this.frame = frame;
    }

}
