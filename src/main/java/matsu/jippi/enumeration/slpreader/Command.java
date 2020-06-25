package matsu.jippi.enumeration.slpreader;

public enum Command {
    MESSAGE_SIZES("0x35"), GAME_START("0x36"), PRE_FRAME_UPDATE("0x37"), POST_FRAME_UPDATE("0x38"), GAME_END("0x39"),
    ITEM_UPDATE("0x3B"), FRAME_BOOKEND("0x3C");

    private String hex;

    private Command(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
