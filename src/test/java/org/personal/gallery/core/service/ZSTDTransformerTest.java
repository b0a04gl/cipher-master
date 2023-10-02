package org.personal.gallery.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.gallery.core.models.CipherRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.personal.gallery.utils.TestUtils.*;

public class ZSTDTransformerTest {

    private ZSTDTransformer zstdTransformer;
    public static final String SAMPLE_COMPRESSED_PAYLOAD_ZSTD = "KLUv/SA3uQEAeyJtYWtlIjoibWVyY2VkZXMiLCJtb2RlbCI6ImdsZTYzQ291cGVTIiwieWVhciI6IjIwMjMifQ==";

    @BeforeEach
    void init(){
        zstdTransformer = new ZSTDTransformer();
    }

    @Test
    void shouldCompressAsExpected() {
        var mockRequest = getObjectFromFile(COMPRESS_CIPHER_REQUEST_JSON_FILEPATH, CipherRequest.class);
        assertNotNull(mockRequest);
        byte[] payloadBytes = getBytesForCompression(mockRequest.payload());

        var compressed = zstdTransformer.compress(payloadBytes);

        assertEquals(SAMPLE_COMPRESSED_PAYLOAD_ZSTD,compressed);
    }

    @Test
    void shouldDecompressAsExpected() {

        byte[] payloadBytes = getBytesForDecompression(SAMPLE_COMPRESSED_PAYLOAD_ZSTD);

        String decompressed = zstdTransformer.decompress(payloadBytes);

        assertEquals(SAMPLE_DECOMPRESSED_PAYLOAD,decompressed);
    }
}
