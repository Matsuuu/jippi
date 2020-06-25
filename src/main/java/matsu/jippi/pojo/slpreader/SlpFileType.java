package matsu.jippi.pojo.slpreader;

import java.util.Map;

import matsu.jippi.interfaces.SlpRefType;

public class SlpFileType {
    private SlpRefType ref;
    private float rawDataPosition;
    private int rawDataLength;
    private float metadataPosition;
    private int metadataLength;
    private Map<Integer, Integer> messageSizes;

    public SlpRefType getRef() {
        return ref;
    }

    public void setRef(SlpRefType ref) {
        this.ref = ref;
    }

    public float getRawDataPosition() {
        return rawDataPosition;
    }

    public void setRawDataPosition(float rawDataPosition) {
        this.rawDataPosition = rawDataPosition;
    }

    public int getRawDataLength() {
        return rawDataLength;
    }

    public void setRawDataLength(int rawDataLength) {
        this.rawDataLength = rawDataLength;
    }

    public float getMetadataPosition() {
        return metadataPosition;
    }

    public void setMetadataPosition(float metadataPosition) {
        this.metadataPosition = metadataPosition;
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

    public SlpFileType(SlpRefType ref, float rawDataPosition, int rawDataLength, float metadataPosition,
            int metadataLength, Map<Integer, Integer> messageSizes) {
        this.ref = ref;
        this.rawDataPosition = rawDataPosition;
        this.rawDataLength = rawDataLength;
        this.metadataPosition = metadataPosition;
        this.metadataLength = metadataLength;
        this.messageSizes = messageSizes;
    }

}
