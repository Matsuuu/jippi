package matsu.jippi.enumeration.slpreader;

public enum SlpInputSource {
    BUFFER("buffer"), FILE("file");

    private String source;

    private SlpInputSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
