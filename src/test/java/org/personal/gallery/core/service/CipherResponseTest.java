package org.personal.gallery.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.core.models.CipherResponse;
import org.personal.gallery.utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CipherResponseTest {
    @Test
    void shouldCreateCipherResponseAsExpected() {
        var transformed = CipherResponse.from(TestUtils.SAMPLE_DECOMPRESSED_PAYLOAD);

        assertEquals(62L,transformed.size());
        assertEquals(TestUtils.SAMPLE_DECOMPRESSED_PAYLOAD, transformed.payload());
    }
}
