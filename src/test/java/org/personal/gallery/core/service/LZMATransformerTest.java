package org.personal.gallery.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.gallery.core.models.CipherRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.personal.gallery.utils.TestUtils.*;
import static org.personal.gallery.utils.TestUtils.SAMPLE_DECOMPRESSED_PAYLOAD;

class LZMATransformerTest {

    private LZMATransformer lzmaTransformer;
    public static final String SAMPLE_COMPRESSED_PAYLOAD_LZMA = "/Td6WFoAAATm1rRGAgAhARYAAAB0L+WjAQA2eyJtYWtlIjoibWVyY2VkZXMiLCJtb2RlbCI6ImdsZTYzQ291cGVTIiwieWVhciI6IjIwMjMifQAATGCdOeVv+I0AAU837kPa7x+2830BAAAAAARZWg==";

    @BeforeEach
    void init(){
        lzmaTransformer = new LZMATransformer();
    }

    @Test
    void shouldCompressAsExpected() {
        var mockRequest = getObjectFromFile(COMPRESS_CIPHER_REQUEST_JSON_FILEPATH, CipherRequest.class);
        assertNotNull(mockRequest);
        byte[] payloadBytes = getBytesForCompression(mockRequest.payload());

        var compressed = lzmaTransformer.compress(payloadBytes);

        assertEquals(SAMPLE_COMPRESSED_PAYLOAD_LZMA,compressed);
    }

    @Test
    void shouldDecompressAsExpected() {
        var mockRequest = getObjectFromFile(DECOMPRESS_CIPHER_REQUEST_JSON_FILEPATH, CipherRequest.class);
        assertNotNull(mockRequest);
        byte[] payloadBytes = getBytesForDecompression(SAMPLE_COMPRESSED_PAYLOAD_LZMA);

        String decompressed = lzmaTransformer.decompress(payloadBytes);

        assertEquals(SAMPLE_DECOMPRESSED_PAYLOAD,decompressed);
    }
}
