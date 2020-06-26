package matsu.jippi.pojo.slpreader;

import java.nio.ByteBuffer;

import matsu.jippi.enumeration.slpreader.SlpInputSource;
import matsu.jippi.interfaces.SlpRefType;

public class SlpBufferSourceRef implements SlpRefType {
    private SlpInputSource source;
    private ByteBuffer buffer;

    public SlpInputSource getSource() {
        return source;
    }

    public void setSource(SlpInputSource source) {
        this.source = source;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public SlpBufferSourceRef() {
    }

    public SlpBufferSourceRef(SlpInputSource source, ByteBuffer buffer) {
        this.source = source;
        this.buffer = buffer;
    }
}
