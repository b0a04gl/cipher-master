package org.personal.gallery.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class TestUtils {

    public static final String COMPRESS_CIPHER_REQUEST_JSON_FILEPATH = "classpath:SampleCompressCipherRequest.json";
    public static final String COMPRESS_CIPHER_RESPONSE_JSON_FILEPATH = "classpath:SampleCompressedResponse.json";
    public static final String DECOMPRESS_CIPHER_RESPONSE_JSON_FILEPATH = "classpath:SampleDecompressedResponse.json";
    public static final String DECOMPRESS_CIPHER_REQUEST_JSON_FILEPATH = "classpath:SampleDecompressCipherRequest.json";
    public static final String INVALID_CIPHER_REQUEST_JSON_FILEPATH = "classpath:SampleInvalidRequest.json";
    public static final String SAMPLE_COMPRESSED_PAYLOAD_GZIP = "H4sIAAAAAAAA/6tWyk3MTlWyUspNLUpOTUktVtJRys1PSc0BCqXnpJoZO+eXFqQGA0UrUxOLgIJGBkbGSrUAUsW3MjcAAAA=";
    public static final String SAMPLE_DECOMPRESSED_PAYLOAD = "{\"make\":\"mercedes\",\"model\":\"gle63CoupeS\",\"year\":\"2023\"}";

    public static <T> T getObjectFromFile(String fileName, Class<T> targetClass) {
        try {
            File resource = ResourceUtils.getFile(fileName);
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(resource, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInputAsString(String filePath) {
        File resource = null;
        try {
            resource = ResourceUtils.getFile(filePath);
            return Files.readString(resource.toPath(), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] getBytesForCompression(Object payload) {
        ObjectMapper mapper = JsonMapper.builder().addModule(new AfterburnerModule()).build();
        try {
            return mapper.writeValueAsBytes(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytesForDecompression(Object payload) {
        return Base64.getDecoder().decode(String.valueOf(payload));
    }
}
