package matsu.jippi.enumeration.slpreader;

public enum Command {
    MESSAGE_SIZES(0x35), GAME_START(0x36), PRE_FRAME_UPDATE(0x37), POST_FRAME_UPDATE(0x38), GAME_END(0x39),
    ITEM_UPDATE(0x3B), FRAME_BOOKEND(0x3C);

    private int hex;

    private Command(int hex) {
        this.hex = hex;
    }

    public int getHex() {
        return hex;
    }

    public static Command from(int hex) {
        for (Command c : Command.values()) {
            if (c.getHex() == hex) {
                return c;
            }
        }
        return null;
    }
}
