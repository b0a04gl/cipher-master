package org.personal.gallery.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.utils.TestUtils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.personal.gallery.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class CipherServiceFacadeTest {


    private CipherServiceFacade cipherServiceFacade;


    static Stream<Arguments> supplyArgs() {
        return Stream.of(
                Arguments.of(0,
                        COMPRESS_CIPHER_REQUEST_JSON_FILEPATH,
                        103L,
                        SAMPLE_COMPRESSED_PAYLOAD_GZIP),
                Arguments.of(1,
                        DECOMPRESS_CIPHER_REQUEST_JSON_FILEPATH,
                        62L,
                        SAMPLE_DECOMPRESSED_PAYLOAD));
    }

    @BeforeEach
    void init() {
        cipherServiceFacade = new CipherServiceFacade(List.of(new GZIPTransformer()));
    }

    @ParameterizedTest(name = "operation={0}")
    @MethodSource("supplyArgs")
    void shouldHandleRequestAsExpected(int operationCode, String inputFile, long expectedSize, String expectedOutputPayload) {

        var mockRequest = TestUtils.getObjectFromFile(inputFile, CipherRequest.class);

        var cipherResponse = cipherServiceFacade.handleRequest(mockRequest, operationCode);

        assertEquals(expectedSize, cipherResponse.size());
        assertEquals(expectedOutputPayload, cipherResponse.payload());
    }
}
