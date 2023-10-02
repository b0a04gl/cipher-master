package org.personal.gallery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.gallery.application.CipherController;
import org.personal.gallery.exceptions.CustomExceptionHandler;
import org.personal.gallery.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.personal.gallery.utils.TestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
class CipherMasterIntegrationTest {
    @Autowired
    private CipherController cipherController;

    private MockMvc mockMvc;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(cipherController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void shouldHandleCompressionRequest() throws Exception {
        //Arrange
        var request = MockMvcRequestBuilders
                .post("/cipher/compress")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.getInputAsString(COMPRESS_CIPHER_REQUEST_JSON_FILEPATH));

        ResultActions resultActions = mockMvc.perform(request);

        String expectedContentString = TestUtils.getInputAsString(COMPRESS_CIPHER_RESPONSE_JSON_FILEPATH);
        resultActions
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertEquals(expectedContentString,result.getResponse().getContentAsString()));

    }

    @Test
    void shouldHandleDecompressionRequest() throws Exception {
        //Arrange
        var request = MockMvcRequestBuilders
                .post("/cipher/decompress")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.getInputAsString(DECOMPRESS_CIPHER_REQUEST_JSON_FILEPATH));

        ResultActions resultActions = mockMvc.perform(request);

        String expectedContentString = TestUtils.getInputAsString(DECOMPRESS_CIPHER_RESPONSE_JSON_FILEPATH);
        resultActions
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertEquals(expectedContentString,result.getResponse().getContentAsString()));

    }
}
