package org.personal.gallery.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.core.ports.CipherService;
import org.personal.gallery.exceptions.CustomExceptionHandler;
import org.personal.gallery.exceptions.InvalidRequestException;
import org.personal.gallery.utils.TestUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CipherControllerTest {


    private MockMvc mockMvc;
    @Mock
    private RequestValidator requestValidator;
    @Mock
    private CipherService cipherService;

    @InjectMocks
    private CipherController cipherController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cipherController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }


    @Test
    void shouldHandleCompressRequest() throws Exception {
        //Arrange
        var request = MockMvcRequestBuilders
                .post("/cipher/compress")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.getInputAsString("classpath:SampleCompressCipherRequest.json"));
        //act
        var resultActions = mockMvc.perform(request).andDo(print());

        //assert
        resultActions.andExpect(status().isOk());
        Mockito.verify(requestValidator, Mockito.times(1)).validateRequest(any(CipherRequest.class));
        Mockito.verify(cipherService, Mockito.times(1)).handleRequest(any(CipherRequest.class), any(Integer.class));

    }

    @Test
    void shouldHandleDecompressRequest() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/cipher/decompress")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.getInputAsString("classpath:SampleDecompressCipherRequest.json"));


        var resultActions = mockMvc.perform(request);

        resultActions.andExpect(status().isOk());
        Mockito.verify(requestValidator, Mockito.times(1)).validateRequest(any(CipherRequest.class));
        Mockito.verify(cipherService, Mockito.times(1)).handleRequest(any(CipherRequest.class), any(Integer.class));
    }

    @Test
    void shouldThrowExceptionForInvalidRequest() throws Exception {
        when(requestValidator.validateRequest(any(CipherRequest.class))).thenThrow(new InvalidRequestException("missing mandatory headers"));
        String inputAsString = TestUtils.getInputAsString("classpath:SampleInvalidRequest.json");
        var request = MockMvcRequestBuilders
                .post("/cipher/compress")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputAsString);

        var resultActions = mockMvc.perform(request).andDo(print());

        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue( result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> Assertions.assertEquals("missing mandatory headers", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
