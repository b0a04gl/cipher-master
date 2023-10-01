package org.personal.gallery.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TestUtils {

    public static <T> T getObjectFromFile(String fileName, Class<T> targetClass) {
        try {
            File resource = ResourceUtils.getFile(fileName);
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(resource, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInputAsString(String filePath){
        File resource = null;
        try {
            resource = ResourceUtils.getFile(filePath);
            return Files.readString(resource.toPath(), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
