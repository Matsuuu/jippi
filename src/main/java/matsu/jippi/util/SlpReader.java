package matsu.jippi.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.slpreader.Command;
import matsu.jippi.interfaces.EventCallbackFunc;
import matsu.jippi.interfaces.EventPayloadTypes;
import matsu.jippi.interfaces.SlpRefType;
import matsu.jippi.pojo.common.FrameBookendType;
import matsu.jippi.pojo.common.GameEndType;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.ItemUpdateType;
import matsu.jippi.pojo.common.PlayerType;
import matsu.jippi.pojo.common.PostFrameUpdateType;
import matsu.jippi.pojo.common.PreFrameUpdateType;
import matsu.jippi.pojo.slpreader.SlpBufferSourceRef;
import matsu.jippi.pojo.slpreader.SlpFileSourceRef;
import matsu.jippi.pojo.slpreader.SlpFileType;
import matsu.jippi.pojo.slpreader.SlpReadInput;

public class SlpReader {

    public SlpRefType getRef(SlpReadInput input) throws IOException {
        switch (input.getSource()) {
            case BUFFER:
                return new SlpBufferSourceRef(input.getSource(), input.getBuffer());
            case FILE:
                return new SlpFileSourceRef(input.getSource(),
                        Integer.parseInt(FileReader.readFileFromPath(input.getFilePath())));
            default:
                throw new IOException("Source type not supported");
        }
    }

    public int readRef(SlpRefType ref, ByteBuffer buffer, int offset, int length, int position) throws IOException {
        switch (ref.getSource()) {
            case BUFFER:
                buffer = ((SlpBufferSourceRef) ref).getBuffer().duplicate();
                return buffer.getInt();
            case FILE:
                buffer = ByteBuffer.wrap(FileReader
                        .readFileFromPath(Integer.toString(((SlpFileSourceRef) ref).getFileDescriptor())).getBytes());
                return buffer.getInt();
            default:
                throw new IOException("Source type not supported");

        }
    }

    public int getLenRef(SlpRefType ref) throws IOException {
        switch (ref.getSource()) {
            case BUFFER:
                return ((SlpBufferSourceRef) ref).getBuffer().position();
            case FILE:
                return ((SlpFileSourceRef) ref).getFileDescriptor();
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

        int rawDataLength = buffer.get(0) << 24 | buffer.get(1) << 16 | buffer.get(2) << 8 | buffer.get(3);
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
        for (int i = 0; i < payloadLength; i += 3) {
            int command = messageSizesBuffer.get(i);
            messageSizes.put(command, messageSizesBuffer.get(i + 1) << 8 | messageSizesBuffer.get(i + 2));
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
            if (!commandPayloadBuffers.containsKey(commandByte)) {
                return readPosition;
            }

            ByteBuffer buffer = commandPayloadBuffers.get(commandByte);
            if (buffer.capacity() > stopReadingAt - readPosition) {
                return readPosition;
            }

            readRef(ref, buffer, 0, buffer.capacity(), readPosition);
            // Parsemessage
        }

        return 1;
    }

    public EventPayloadTypes parseMessage(Command command, ByteBuffer payload) {
        switch (command) {
            case GAME_START:
                List<PlayerType> players = new ArrayList<>(Arrays.asList(0, 1, 2, 3)).stream().map(playerIndex -> {
                    int cfOffset = playerIndex * 0x8;
                    int dashback = payload.get(0x141 + cfOffset);
                    int shieldDrop = payload.get(0x145 + cfOffset);
                    String cfOption = "None";
                    if (dashback != shieldDrop) {
                        cfOption = "Mixed";
                    } else if (dashback == 1) {
                        cfOption = "UCF";
                    } else if (dashback == 2) {
                        cfOption = "Dween";
                    }

                    int nametagOffset = playerIndex * 0x10;
                    int nametagStart = 0x161 + nametagOffset;
                    ByteBuffer nametagBuf = ByteBuffer
                            .wrap(Arrays.copyOfRange(payload.array(), nametagStart, nametagStart + 16));
                    String nameTag = "";
                    int offset = playerIndex * 0x24;

                    return new PlayerType(playerIndex, playerIndex + 1, payload.getInt(0x65 + offset),
                            payload.getInt(0x68 + offset), payload.getInt(0x67 + offset), payload.getInt(0x66 + offset),
                            payload.getInt(0x6E + offset), cfOption, nameTag);
                }).collect(Collectors.toCollection(ArrayList::new));

                return new GameStartType(payload.getInt(0x1) + "." + payload.getInt(0x2) + "." + payload.getInt(0x3),
                        payload.getInt(0xD) == 1, payload.getInt(0x1A1) == 1, payload.getInt(0x13), players);
            case PRE_FRAME_UPDATE:
                return new PreFrameUpdateType(payload.getInt(0x1), payload.getInt(0x5), payload.getInt(0x6) == 1,
                        payload.getInt(0x7), payload.getInt(0xB), payload.getInt(0xD), payload.getInt(0x11),
                        payload.getInt(0x15), payload.getInt(0x19), payload.getInt(0x1D), payload.getInt(0x21),
                        payload.getInt(0x25), payload.getInt(0x29), payload.getInt(0x2D), payload.getInt(0x31),
                        payload.getInt(0x33), payload.getInt(0x37), payload.getInt(0x3C));
            case POST_FRAME_UPDATE:
                return new PostFrameUpdateType(payload.getInt(0x1), payload.getInt(0x5), payload.get(0x6) == 1,
                        payload.getInt(0x7), payload.getInt(0x8), payload.getFloat(0xA), payload.getFloat(0xE),
                        payload.getInt(0x12), payload.getFloat(0x16), payload.getFloat(0x1A), payload.getInt(0x1E),
                        payload.getInt(0x1F), payload.getInt(0x20), payload.getInt(0x21), payload.getInt(0x22),
                        payload.getInt(0x33));
            case ITEM_UPDATE:
                return new ItemUpdateType(payload.getInt(0x1), payload.getInt(0x5), payload.getInt(0x7),
                        payload.getFloat(0x8), payload.getFloat(0xC), payload.getFloat(0x10), payload.getFloat(0x14),
                        payload.getFloat(0x18), payload.getFloat(0x1C), payload.getFloat(0x1E), payload.getInt(0x20));
            case FRAME_BOOKEND:
                return new FrameBookendType(payload.getInt(0x1));
            case GAME_END:
                return new GameEndType(payload.getInt(0x1), payload.getInt(0x2));
        }
        return null;
    }
}
