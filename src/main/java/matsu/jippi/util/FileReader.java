package matsu.jippi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    public static String readFileFromPath(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return "NO_FILE_FOUND";
        }
    }
}
