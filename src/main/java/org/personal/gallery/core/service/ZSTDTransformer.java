package org.personal.gallery.core.service;

import com.github.luben.zstd.Zstd;
import org.personal.gallery.core.ports.BaseTransformer;
import org.personal.gallery.exceptions.TransformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static org.personal.gallery.utils.ApplicationConstants.ZSTD;

@Component
public class ZSTDTransformer implements BaseTransformer {
    private final Logger logger = LoggerFactory.getLogger(ZSTDTransformer.class);

    @Override
    public String getCompressionType() {
        return ZSTD.getValue();
    }

    @Override
    public String compress(byte[] payloadBytes) {
        byte[] compressBytes = Zstd.compress(payloadBytes);
        return Base64.getEncoder().encodeToString(compressBytes);
    }

    @Override
    public String decompress(byte[] payloadBytes) {
        try {
            int size = (int) Zstd.decompressedSize(payloadBytes);
            byte[] actualData = Zstd.decompress(payloadBytes, size);
            return new String(actualData);
        } catch (Exception e) {
            logger.error("error in de-compressing the data with ZSTD", e);
            throw new TransformationException("Error in de-compressing with ZSTD");
        }
    }
}
