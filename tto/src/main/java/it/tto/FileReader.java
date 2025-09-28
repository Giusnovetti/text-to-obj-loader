package it.tto;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;


class FileReader {


    public static byte[] readFileAsBytes(String filePath) throws IOException {
        Path path = Path.of(filePath);
        return Files.readAllBytes(path);
    }

    public static String getFileName(String filePath) {
        Path path = Path.of(filePath);
        return path.getFileName().toString();
    }

    public static String getFileNameExt(String filePath) {
        Path path = Path.of(filePath);
        String filename = path.getFileName().toString();
        int extensionIndex =filename.lastIndexOf('.');
        if(extensionIndex == 0)
            throw new IllegalArgumentException("file without extension!");

        return filename.substring(extensionIndex);
    }

    public static Properties readProperties(String filePath) throws IOException {

        try (FileInputStream fis = new FileInputStream(filePath)){
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        }catch (Exception e){
            throw  new IOException(e);
        }

    }

}


