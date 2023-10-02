package org.personal.gallery.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.gallery.core.models.CipherRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.personal.gallery.utils.TestUtils.*;

class GZIPTransformerTest {

    private GZIPTransformer gzipTransformer;

    @BeforeEach
    void init(){
        gzipTransformer = new GZIPTransformer();
    }

    @Test
    void shouldCompressAsExpected() {
        var mockRequest = getObjectFromFile(COMPRESS_CIPHER_REQUEST_JSON_FILEPATH, CipherRequest.class);
        assertNotNull(mockRequest);
        byte[] payloadBytes = getBytesForCompression(mockRequest.payload());

        var compressed = gzipTransformer.compress(payloadBytes);

        assertEquals(SAMPLE_COMPRESSED_PAYLOAD_GZIP,compressed);
    }

    @Test
    void shouldDecompressAsExpected() {
        byte[] payloadBytes = getBytesForDecompression(SAMPLE_COMPRESSED_PAYLOAD_GZIP);

        String decompressed = gzipTransformer.decompress(payloadBytes);

        assertEquals(SAMPLE_DECOMPRESSED_PAYLOAD,decompressed);
    }

}
