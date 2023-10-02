package org.personal.gallery.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.exceptions.InvalidRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestValidatorTest {

    private RequestValidator requestValidator;

    @BeforeEach
    void init() {
        requestValidator = new RequestValidator();
    }

    @Test
    void shouldValidateRequestAsExpected() {
        var mockRequest = mock(CipherRequest.class);
        when(mockRequest.compressionType()).thenReturn("LZMA");
        when(mockRequest.payload()).thenReturn("sample payload object");

        var actualResult = requestValidator.validateRequest(mockRequest);

        assertTrue(actualResult);
    }

    @Test
    void shouldThrowExceptionForInvalidRequestAsExpected() {
        var mockRequest = mock(CipherRequest.class);
        when(mockRequest.compressionType()).thenReturn("LZMA");

        var invalidRequestException = Assertions.assertThrows(InvalidRequestException.class, () -> requestValidator.validateRequest(mockRequest));

        assertEquals("Request is missing some mandatory fields!", invalidRequestException.errorMessage);
    }
}
