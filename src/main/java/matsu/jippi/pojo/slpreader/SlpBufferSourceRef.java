package matsu.jippi.pojo.slpreader;

import java.nio.Buffer;

import matsu.jippi.enumeration.slpreader.SlpInputSource;
import matsu.jippi.interfaces.SlpRefType;

public class SlpBufferSourceRef implements SlpRefType {
    private SlpInputSource source;
    private Buffer buffer;

    public SlpInputSource getSource() {
        return source;
    }

    public void setSource(SlpInputSource source) {
        this.source = source;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public SlpBufferSourceRef() {
    }

    public SlpBufferSourceRef(SlpInputSource source, Buffer buffer) {
        this.source = source;
        this.buffer = buffer;
    }
}
