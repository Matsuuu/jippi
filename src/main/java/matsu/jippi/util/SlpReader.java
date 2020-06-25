package matsu.jippi.util;

import java.io.IOException;

import matsu.jippi.interfaces.SlpRefType;
import matsu.jippi.pojo.slpreader.SlpBufferSourceRef;
import matsu.jippi.pojo.slpreader.SlpFileSourceRef;
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
    // TODO: Continue from
    // https://github.com/project-slippi/slp-parser-js/blob/master/src/utils/slpReader.ts:166
}
