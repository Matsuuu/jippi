package matsu.jippi.pojo.slpreader;

import matsu.jippi.enumeration.slpreader.SlpInputSource;
import matsu.jippi.interfaces.SlpRefType;

public class SlpFileSourceRef implements SlpRefType {
    private SlpInputSource source;
    private int fileDescriptor;

    public SlpInputSource getSource() {
        return source;
    }

    public void setSource(SlpInputSource source) {
        this.source = source;
    }

    public int getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(int fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

    public SlpFileSourceRef() {
    }

    public SlpFileSourceRef(SlpInputSource source, int fileDescriptor) {
        this.source = source;
        this.fileDescriptor = fileDescriptor;
    }

}
