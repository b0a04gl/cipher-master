package org.personal.gallery.application;

import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.core.models.CipherResponse;
import org.personal.gallery.core.ports.CipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CipherController {
    private final Logger logger = LoggerFactory.getLogger(CipherController.class);
    private final RequestValidator requestValidator;
    private final CipherService cipherService;

    @Autowired
    public CipherController(RequestValidator requestValidator, CipherService cipherService) {
        this.requestValidator = requestValidator;
        this.cipherService = cipherService;
    }

    @PostMapping(path = "/cipher/compress", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<CipherResponse> handleCompression(@RequestBody CipherRequest cipherRequest) throws Exception {
        requestValidator.validateRequest(cipherRequest);
        logger.info("API hit for compressing data with {} compressionType", cipherRequest.compressionType());
        return ResponseEntity.ok(cipherService.handleRequest(cipherRequest,0));
    }

    @PostMapping(path = "/cipher/decompress", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<CipherResponse> handleDecompression(@RequestBody CipherRequest cipherRequest) throws Exception {
        requestValidator.validateRequest(cipherRequest);
        logger.info("API hit for decompressing data with {} compressionType", cipherRequest.compressionType());
        return ResponseEntity.ok(cipherService.handleRequest(cipherRequest,1));
    }
}
