package org.personal.gallery.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.github.luben.zstd.Zstd;
import org.personal.gallery.core.ports.BaseTransformer;
import org.personal.gallery.exceptions.TransformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

import static org.personal.gallery.utils.ApplicationConstants.ZSTD;

@Component
public class ZSTDTransformer implements BaseTransformer {
    private final Logger logger = LoggerFactory.getLogger(ZSTDTransformer.class);
    private static final ObjectMapper mapper = JsonMapper.builder().addModule(new AfterburnerModule()).build();

    @Override
    public String getCompressionType() {
        return ZSTD.getValue();
    }

    @Override
    public String compress(byte[] payloadBytes) {
        try {
            byte[] compressBytes = Zstd.compress(mapper.writeValueAsBytes(payloadBytes));
            return Base64.getEncoder().encodeToString(compressBytes);
        } catch (IOException e) {
            logger.error("error in compressing the data with ZSTD", e);
            throw new TransformationException("Error in compressing with ZSTD");
        }

    }

    @Override
    public String decompress(byte[] payloadBytes) {
        try {
            byte[] decoded = Base64.getDecoder().decode(payloadBytes);
            int size = (int) Zstd.decompressedSize(decoded);
            byte[] actualData = Zstd.decompress(decoded, size);
            return new String(actualData);
        } catch (Exception e) {
            logger.error("error in de-compressing the data with ZSTD", e);
            throw new TransformationException("Error in de-compressing with ZSTD");
        }
    }
}
