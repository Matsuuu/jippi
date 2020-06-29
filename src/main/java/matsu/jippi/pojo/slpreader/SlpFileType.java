package matsu.jippi.pojo.slpreader;

import java.util.Map;

import matsu.jippi.interfaces.SlpRefType;

public class SlpFileType {
    private SlpRefType ref;
    private int rawDataPosition;
    private int rawDataLength;
    private int metadataPosition;
    private int metadataLength;
    private Map<Integer, Integer> messageSizes;

    public SlpRefType getRef() {
        return ref;
    }

    public void setRef(SlpRefType ref) {
        this.ref = ref;
    }

    public int getRawDataLength() {
        return rawDataLength;
    }

    public void setRawDataLength(int rawDataLength) {
        this.rawDataLength = rawDataLength;
    }

    public int getMetadataLength() {
        return metadataLength;
    }

    public void setMetadataLength(int metadataLength) {
        this.metadataLength = metadataLength;
    }

    public Map<Integer, Integer> getMessageSizes() {
        return messageSizes;
    }

    public void setMessageSizes(Map<Integer, Integer> messageSizes) {
        this.messageSizes = messageSizes;
    }

    public SlpFileType() {
    }

    public int getRawDataPosition() {
        return rawDataPosition;
    }

    public void setRawDataPosition(int rawDataPosition) {
        this.rawDataPosition = rawDataPosition;
    }

    public int getMetadataPosition() {
        return metadataPosition;
    }

    public void setMetadataPosition(int metadataPosition) {
        this.metadataPosition = metadataPosition;
    }

    public SlpFileType(SlpRefType ref, int rawDataPosition, int rawDataLength, int metadataPosition, int metadataLength,
            Map<Integer, Integer> messageSizes) {
        this.ref = ref;
        this.rawDataPosition = rawDataPosition;
        this.rawDataLength = rawDataLength;
        this.metadataPosition = metadataPosition;
        this.metadataLength = metadataLength;
        this.messageSizes = messageSizes;
    }

}
