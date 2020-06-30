package matsu.jippi.enumeration.inputs;

public enum JoystickRegion {
    DZ(0), NE(1), SE(2), SW(3), NW(4), N(5), E(6), S(7), W(8);

    private int region;

    private JoystickRegion(int region) {
        this.setRegion(region);
    }

    public int getRegion() {
        return region;
    }

}
