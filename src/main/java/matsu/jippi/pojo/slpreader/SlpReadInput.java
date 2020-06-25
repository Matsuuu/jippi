package matsu.jippi.pojo.slpreader;

import java.nio.Buffer;

import matsu.jippi.enumeration.slpreader.SlpInputSource;

public class SlpReadInput {

    private SlpInputSource source;
    private String filePath;
    private Buffer buffer;

    public SlpReadInput(SlpInputSource source, String filePath, Buffer buffer) {
        this.source = source;
        this.filePath = filePath;
        this.buffer = buffer;
    }

    public SlpInputSource getSource() {
        return source;
    }

    public String getFilePath() {
        return filePath;
    }

    public Buffer getBuffer() {
        return buffer;
    }

}
