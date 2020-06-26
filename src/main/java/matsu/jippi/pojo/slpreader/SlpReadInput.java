package matsu.jippi.pojo.slpreader;

import java.nio.ByteBuffer;

import matsu.jippi.enumeration.slpreader.SlpInputSource;

public class SlpReadInput {

    private SlpInputSource source;
    private String filePath;
    private ByteBuffer buffer;

    public SlpReadInput(SlpInputSource source, String filePath, ByteBuffer buffer) {
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

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setSource(SlpInputSource source) {
        this.source = source;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

}
