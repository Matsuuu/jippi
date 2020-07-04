package matsu.jippi.util;

import matsu.jippi.enumeration.slpreader.Command;
import matsu.jippi.interfaces.EventCallbackFunc;
import matsu.jippi.interfaces.EventPayloadTypes;
import matsu.jippi.interfaces.SlpRefType;
import matsu.jippi.pojo.common.*;
import matsu.jippi.pojo.slpreader.SlpBufferSourceRef;
import matsu.jippi.pojo.slpreader.SlpFileSourceRef;
import matsu.jippi.pojo.slpreader.SlpFileType;
import matsu.jippi.pojo.slpreader.SlpReadInput;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.Character;

public class SlpReader {

    public SlpRefType getRef(SlpReadInput input) throws IOException {
        switch (input.getSource()) {
            case BUFFER:
                return new SlpBufferSourceRef(input.getSource(), input.getBuffer());
            case FILE:
                return new SlpFileSourceRef(input.getSource(), FileReader.readFileFromPath(input.getFilePath()));
            default:
                throw new IOException("Source type not supported");
        }
    }

    public int readRef(SlpRefType ref, ByteBuffer buffer, int offset, int length, int position) throws IOException {
        switch (ref.getSource()) {
            case BUFFER:
                SlpBufferSourceRef bufferSourceRef = (SlpBufferSourceRef) ref;
                byte[] bufferBytes = bufferSourceRef.getBuffer().array();
                byte[] targetBytes = new byte[length];
                ByteBuffer copiedBuffer = ByteBuffer.wrap(bufferBytes);
                copiedBuffer.position(position);
                copiedBuffer.get(targetBytes, offset, length);

                buffer.position(offset);
                buffer.put(targetBytes);
                return buffer.position();
            case FILE:
                SlpFileSourceRef fileSourceRef = (SlpFileSourceRef) ref;

                ByteBuffer bytes = ByteBuffer.allocate(length);
                fileSourceRef.getFileInputStream().getChannel().read(bytes, position);
                buffer.put(bytes.array(), offset, length);
                return buffer.position();
            default:
                throw new IOException("Source type not supported");

        }
    }

    public int getLenRef(SlpRefType ref) throws IOException {
        switch (ref.getSource()) {
            case BUFFER:
                return ((SlpBufferSourceRef) ref).getBuffer().capacity();
            case FILE:
                return (int) ((SlpFileSourceRef) ref).getFileInputStream().getChannel().size();
            default:
                throw new IOException("Source type not supported");

        }
    }

    public SlpFileType openSlpFile(SlpReadInput input) throws IOException {
        SlpRefType ref = getRef(input);

        int rawDataPosition = getRawDataPosition(ref);
        int rawDataLength = getRawDataLength(ref, rawDataPosition);
        int metadataPosition = rawDataPosition + rawDataLength + 10;
        int metadataLength = getMetaDataLength(ref, metadataPosition);
        Map<Integer, Integer> messageSizes = getMessageSizes(ref, rawDataPosition);

        return new SlpFileType(ref, rawDataPosition, rawDataLength, metadataPosition, metadataLength, messageSizes);
    }

    public void closeSlpFile(SlpFileType file) throws IOException {
        switch (file.getRef().getSource()) {
            case FILE:
                SlpFileSourceRef sourceRef = (SlpFileSourceRef) file.getRef();
                sourceRef.getFileInputStream().close();
                break;
        }
    }

    private int getRawDataPosition(SlpRefType ref) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        readRef(ref, buffer, 0, buffer.capacity(), 0);

        if (buffer.get(0) == 0x36) {
            return 0;
        }

        if (buffer.get(0) != Character.codePointAt("{", 0)) {
            return 0;
        }

        return 15;
    }

    private int getRawDataLength(SlpRefType ref, int position) throws IOException {
        int fileSize = getLenRef(ref);
        if (position == 0) {
            return fileSize;
        }

        ByteBuffer buffer = ByteBuffer.allocate(4);
        readRef(ref, buffer, 0, buffer.capacity(), position - 4);

        int b1 = Byte.toUnsignedInt(buffer.get(0)) << 24;
        int b2 = Byte.toUnsignedInt(buffer.get(1)) << 16;
        int b3 = Byte.toUnsignedInt(buffer.get(2)) << 8;
        int b4 = Byte.toUnsignedInt(buffer.get(3));

        int rawDataLength = b1 | b2 | b3 | b4;
        if (rawDataLength > 0) {
            return rawDataLength;
        }

        return fileSize - position;
    }

    private int getMetaDataLength(SlpRefType ref, int position) throws IOException {
        return getLenRef(ref) - position - 1;
    }

    private Map<Integer, Integer> getMessageSizes(SlpRefType ref, int position) throws IOException {
        Map<Integer, Integer> messageSizes = new HashMap<>();
        if (position == 0) {
            messageSizes.put(0x36, 0x140);
            messageSizes.put(0x37, 0x6);
            messageSizes.put(0x38, 0x46);
            messageSizes.put(0x39, 0x1);
            return messageSizes;
        }

        ByteBuffer buffer = ByteBuffer.allocate(2);
        readRef(ref, buffer, 0, buffer.capacity(), position);
        if (buffer.get(0) != Command.MESSAGE_SIZES.getHex()) {
            return new HashMap<>();
        }

        int payloadLength = buffer.get(1);
        messageSizes.put(0x35, payloadLength);

        ByteBuffer messageSizesBuffer = ByteBuffer.allocate(payloadLength - 1);
        readRef(ref, messageSizesBuffer, 0, messageSizesBuffer.capacity(), position + 2);
        for (int i = 0; i < payloadLength - 1; i += 3) {
            int command = messageSizesBuffer.get(i);
            messageSizes.put(command, (int) messageSizesBuffer.getShort(i + 1));
        }
        return messageSizes;
    }

    public int iterateEvents(SlpFileType slpFile, EventCallbackFunc callback, Integer startPos) throws IOException {

        SlpRefType ref = slpFile.getRef();
        int readPosition = startPos != null ? startPos : slpFile.getRawDataPosition();
        int stopReadingAt = slpFile.getRawDataPosition() + slpFile.getRawDataLength();

        Map<Integer, ByteBuffer> commandPayloadBuffers = new HashMap<>();
        for (Integer key : slpFile.getMessageSizes().keySet()) {
            commandPayloadBuffers.put(key, ByteBuffer.allocate(slpFile.getMessageSizes().get(key) + 1));
        }

        ByteBuffer commandByteBuffer = ByteBuffer.allocate(1);
        while (readPosition < stopReadingAt) {
            readRef(ref, commandByteBuffer, 0, 1, readPosition);
            int commandByte = commandByteBuffer.get(0);
            Command commandFromByte = Command.from(commandByte);
            if (!commandPayloadBuffers.containsKey(commandByte)) {
                return readPosition;
            }

            ByteBuffer buffer = commandPayloadBuffers.get(commandByte);
            if (buffer.capacity() > stopReadingAt - readPosition) {
                return readPosition;
            }

            buffer.clear();
            readRef(ref, buffer, 0, buffer.capacity(), readPosition);
            EventPayloadTypes parsedPayload = parseMessage(commandFromByte, buffer);
            boolean shouldStop = callback.callback(commandFromByte, parsedPayload);

            if (shouldStop) {
                break;
            }
            commandByteBuffer.clear();
            readPosition += buffer.capacity();
        }

        return 1;
    }

    public EventPayloadTypes parseMessage(Command command, ByteBuffer payload) {
        switch (command) {
            case GAME_START:
                List<PlayerType> players = new ArrayList<>(Arrays.asList(0, 1, 2, 3)).stream().map(playerIndex -> {
                    int cfOffset = playerIndex * 0x8;
                    Integer dashback = readInt32(payload, 0x141 + cfOffset);
                    Integer shieldDrop = readInt32(payload, 0x145 + cfOffset);
                    String cfOption = "None";
                    if (dashback != shieldDrop) {
                        cfOption = "Mixed";
                    } else if (dashback != null && dashback == 1) {
                        cfOption = "UCF";
                    } else if (dashback != null && dashback == 2) {
                        cfOption = "Dween";
                    }

                    int nametagOffset = playerIndex * 0x10;

                    int nametagStart = 0x161 + nametagOffset;
                    byte[] nametagBufferContent = new byte[16];
                    String nameTag = null;
                    if (nametagStart + 16 < payload.capacity()) {
                        payload.position(nametagStart);
                        payload.get(nametagBufferContent, 0, 16);
                        ByteBuffer nametagBuffer = ByteBuffer.wrap(nametagBufferContent);
                        try {
                            nameTag = new String(nametagBuffer.array(), "Shift_JIS").trim();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    int offset = playerIndex * 0x24;

                    return new PlayerType(playerIndex, playerIndex + 1, readUInt8(payload, 0x65 + offset),
                            readUInt8(payload, 0x68 + offset), readUInt8(payload, 0x67 + offset),
                            readUInt8(payload, 0x66 + offset), readUInt8(payload, 0x6E + offset), cfOption, nameTag);
                }).collect(Collectors.toCollection(ArrayList::new));

                return new GameStartType(
                        readUInt8(payload, 0x1) + "." + readUInt8(payload, 0x2) + "." + readUInt8(payload, 0x3),
                        readBool(payload, 0xD), readBool(payload, 0x1A1), readUInt16(payload, 0x13), players);
            case PRE_FRAME_UPDATE:
                return new PreFrameUpdateType(readInt32(payload, 0x1), readUInt8(payload, 0x5), readBool(payload, 0x6),
                        readUInt32(payload, 0x7), readUInt16(payload, 0xB), readFloat(payload, 0xD),
                        readFloat(payload, 0x11), readFloat(payload, 0x15), readFloat(payload, 0x19),
                        readFloat(payload, 0x1D), readFloat(payload, 0x21), readFloat(payload, 0x25),
                        readFloat(payload, 0x29), readUInt32(payload, 0x2D), readUInt16(payload, 0x31),
                        readFloat(payload, 0x33), readFloat(payload, 0x37), readFloat(payload, 0x3C));
            case POST_FRAME_UPDATE:
                return new PostFrameUpdateType(readInt32(payload, 0x1), readUInt8(payload, 0x5), readBool(payload, 0x6),
                        readUInt8(payload, 0x7), readUInt16(payload, 0x8), readFloat(payload, 0xA),
                        readFloat(payload, 0xE), readFloat(payload, 0x12), readFloat(payload, 0x16),
                        readFloat(payload, 0x1A), readUInt8(payload, 0x1E), readUInt8(payload, 0x1F),
                        readUInt8(payload, 0x20), readUInt8(payload, 0x21), readFloat(payload, 0x22),
                        readUInt8(payload, 0x33));
            case ITEM_UPDATE:
                return new ItemUpdateType(readInt32(payload, 0x1), readUInt16(payload, 0x5), readUInt8(payload, 0x7),
                        readFloat(payload, 0x8), readFloat(payload, 0xC), readFloat(payload, 0x10),
                        readFloat(payload, 0x14), readFloat(payload, 0x18), readUInt16(payload, 0x1C),
                        readUInt16(payload, 0x1E), readUInt32(payload, 0x20));
            case FRAME_BOOKEND:
                return new FrameBookendType(readUInt32(payload, 0x1));
            case GAME_END:
                return new GameEndType(readUInt8(payload, 0x1), readInt8(payload, 0x2));
        }
        return null;
    }

    public MetadataType getMetadata(SlpFileType slpFile) throws IOException {
        if (slpFile.getMetadataLength() <= 0) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(slpFile.getMetadataLength());
        readRef(slpFile.getRef(), buffer, 0, buffer.capacity(), slpFile.getMetadataPosition());

        return MetadataType.parseUBObject(buffer);
    }

    boolean canReadFromPayload(ByteBuffer payload, int offset, int length) {
        int payloadLength = payload.capacity();
        return offset + length <= payloadLength;
    }

    Float readFloat(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 4) ? payload.getFloat(offset) : null;
    }

    Integer readInt32(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 4) ? payload.getInt(offset) : null;
    }

    Integer readInt8(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 4) ? payload.getInt(offset) : null;
    }

    Integer readInt16(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 2) ? payload.getInt(offset) : null;
    }

    Integer readUInt32(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 4) ? Byte.toUnsignedInt(payload.get(offset)) : null;
    }

    Integer readUInt8(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 1) ? Byte.toUnsignedInt(payload.get(offset)) : null;
    }

    Integer readUInt16(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 2) ? Short.toUnsignedInt(payload.getShort(offset)) : null;
    }

    boolean readBool(ByteBuffer payload, int offset) {
        return canReadFromPayload(payload, offset, 1) && payload.get(offset) == 1;
    }
}
