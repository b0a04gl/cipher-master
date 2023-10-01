package org.personal.gallery.core.service;

import org.personal.gallery.core.ports.BaseTransformer;
import org.personal.gallery.exceptions.TransformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.personal.gallery.utils.ApplicationConstants.GZIP;

@Component
public class GZIPTransformer implements BaseTransformer {
    private final Logger logger = LoggerFactory.getLogger(GZIPTransformer.class);

    @Override
    public String getCompressionType() {
        return GZIP.getValue();
    }

    @Override
    public String compress(byte[] payloadBytes) {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(payloadBytes);
            gzipOutputStream.finish();
            byte[] compressBytes = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(compressBytes);

        } catch (IOException e) {
            logger.error("error in compressing the data", e);
            throw new TransformationException("Error in compressing with GZIP");
        }
    }

    @Override
    public String decompress(byte[] payloadBytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payloadBytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {

            byte[] decompressedBytes = gzipInputStream.readAllBytes();
            return new String(decompressedBytes);

        } catch (IOException e) {
            logger.error("error in de-compressing the data", e);
            throw new TransformationException("Error in de-compressing with GZIP");
        }
    }
}
