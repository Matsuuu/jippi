package matsu.jippi.util;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import matsu.jippi.game.SlippiGame;
import matsu.jippi.pojo.common.JippiOutputType;

public class JSONWriter {
    public static void writeToJson(SlippiGame game, String outputPath) throws IOException, JsonGenerationException {
        ObjectMapper mapper = new ObjectMapper();

        JippiOutputType jippiOutput = new JippiOutputType(game);

        mapper.writeValue(Paths.get(outputPath).toFile(), jippiOutput);
    }
}
