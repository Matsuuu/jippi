package matsu.jippi.enumeration.stats;

public enum State {
    DAMAGE_START(0x4B), DAMAGE_END(0x5B), CAPTURE_START(0xDF), CAPTURE_END(0xE8), GUARD_START(0xB2), GUARD_END(0xB6),
    GROUNDED_CONTROL_START(0xE), GROUNDED_CONTROL_END(0x18), SQUAT_START(0x27), SQUAT_END(0x29), DOWN_START(0xB7),
    DOWN_END(0xC6), TECH_START(0xC7), TECH_END(0xCC), DYING_START(0x0), DYING_END(0xA), CONTROLLED_JUMP_START(0x18),
    CONTROLLED_JUMP_END(0x22), GROUND_ATTACK_START(0x2C), GROUND_ATTACK_END(0x40), ROLL_FORWARD(0xE9),
    ROLL_BACKWARD(0xEA), SPOT_DODGE(0xEB), AIR_DODGE(0xEC), ACTION_WAIT(0xE), ACTION_DASH(0x14), ACTION_KNEE_BEND(0x18),
    GUARD_ON(0xB2), TECH_MISS_UP(0xB7), TECH_MISS_DOWN(0xBF), DASH(0x14), TURN(0x12), LANDING_FALL_SPECIAL(0x2B),
    JUMP_FORWARD(0x19), JUMP_BACKWARD(0x1A), FALL_FORWARD(0x1E), FALL_BACKWARD(0x1F), GRAB(0xD4), CLIFF_CATCH(0xFC);

    private int hex;

    public int getHex() {
        return hex;
    }

    private State(int hex) {
        this.hex = hex;
    }

    public static State from(int animation) {
        for (State s : State.values()) {
            if (s.getHex() == animation) {
                return s;
            }
        }
        return null;
    }

}
