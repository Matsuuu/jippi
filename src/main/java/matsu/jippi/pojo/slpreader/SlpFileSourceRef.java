package matsu.jippi.pojo.slpreader;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import matsu.jippi.enumeration.slpreader.SlpInputSource;
import matsu.jippi.interfaces.SlpRefType;

public class SlpFileSourceRef implements SlpRefType {
    private SlpInputSource source;
    private FileDescriptor fileDescriptor;
    private FileInputStream fileInputStream;

    public SlpInputSource getSource() {
        return source;
    }

    public void setSource(SlpInputSource source) {
        this.source = source;
    }

    public SlpFileSourceRef() {
    }

    public FileDescriptor getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(FileDescriptor fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

    public SlpFileSourceRef(SlpInputSource source, FileInputStream fileInputStream) {
        this.source = source;
        this.fileInputStream = fileInputStream;
        try {
            this.fileDescriptor = fileInputStream.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

}
