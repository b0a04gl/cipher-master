package org.personal.gallery.core.service;

import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.core.models.CipherResponse;
import org.personal.gallery.core.ports.BaseTransformer;
import org.personal.gallery.core.ports.CipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.personal.gallery.utils.ApplicationConstants.*;

@Service
public class CipherServiceFacade implements CipherService {

    private final Map<String, BaseTransformer> transformerMap;

    @Autowired
    public CipherServiceFacade(List<BaseTransformer> transformers) {
        transformerMap = transformers.stream().collect(Collectors.toMap(BaseTransformer::getCompressionType, Function.identity()));
    }

    @Override
    public CipherResponse handleRequest(CipherRequest cipherRequest, int operationCode) {
        String compressionType = checkIfProto(cipherRequest.compressionType());
        var serviceTransformer = transformerMap.get(compressionType);
        String transformedResult;
        if (operationCode == 0)
            transformedResult = serviceTransformer.compress(cipherRequest.payload());
        else
            transformedResult = serviceTransformer.decompress(cipherRequest.payload());
        return CipherResponse.from(transformedResult);

    }

    private String checkIfProto(String protoCompression) {
        if(ZSTD_Proto.getValue().equals(protoCompression))
            return ZSTD.value;
        else if(GZIP_Proto.getValue().equals(protoCompression))
            return GZIP.value;
        else
            return protoCompression;
    }
}
