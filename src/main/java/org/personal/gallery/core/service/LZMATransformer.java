package org.personal.gallery.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.personal.gallery.core.ports.BaseTransformer;
import org.personal.gallery.exceptions.TransformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.*;
import java.util.Base64;

import static org.personal.gallery.utils.ApplicationConstants.LZMA;

@Component
public class LZMATransformer implements BaseTransformer {
    private final Logger logger = LoggerFactory.getLogger(LZMATransformer.class);
    private static final ObjectMapper mapper = JsonMapper.builder().addModule(new AfterburnerModule()).build();

    @Override
    public String getCompressionType() {
        return LZMA.getValue();
    }

    @Override
    public String compress(byte[] payloadBytes) {


        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             XZOutputStream xzOutputStream = new XZOutputStream(byteArrayOutputStream, new LZMA2Options())) {
            xzOutputStream.write(mapper.writeValueAsBytes(payloadBytes));
            xzOutputStream.finish();
            byte[] compressedBytes = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(compressedBytes);
        } catch (IOException e) {
            logger.error("error in compressing the data", e);
            throw new TransformationException("Error in compressing with LZMA");
        }

    }

    @Override
    public String decompress(byte[] payloadBytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payloadBytes);
             XZInputStream xzInputStream = new XZInputStream(byteArrayInputStream)) {

            byte[] decompressedBytes = xzInputStream.readAllBytes();
            return new String(decompressedBytes);

        } catch (IOException e) {
            logger.error("error in de-compressing the data", e);
            throw new TransformationException("Error in de-compressing with GZIP");
        }
    }
}

