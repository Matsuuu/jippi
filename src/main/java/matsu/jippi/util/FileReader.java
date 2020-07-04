package matsu.jippi.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public static FileInputStream readFileFromPath(String filePath) {
        try {
            String fullPath = FileReader.getFullPath(filePath);
            File file = new File(fullPath);
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFullPath(String filePath) {
        Path currentDir = Paths.get(".");
        String currentDirPath = currentDir.toAbsolutePath().toString();
        String fullPath = currentDirPath.substring(0, currentDirPath.length() - 2) + "/" + filePath;
        return fullPath;
    }
}
