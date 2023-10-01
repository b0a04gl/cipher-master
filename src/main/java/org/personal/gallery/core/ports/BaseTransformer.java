package org.personal.gallery.core.ports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.personal.gallery.exceptions.TransformationException;

import java.util.Base64;

public interface BaseTransformer {
    String getCompressionType();

    String compress(byte[] payloadBytes);

    String decompress(byte[] payloadBytes);

    default String compress(Object obj) {
        try {
            ObjectMapper mapper = JsonMapper.builder().addModule(new AfterburnerModule()).build();
            byte[] bytes = mapper.writeValueAsBytes(obj);
            return this.compress(bytes);
        } catch (JsonProcessingException e) {
            throw new TransformationException("Error in while reading input object as bytes");
        }
    }

    default String decompress(Object obj) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(String.valueOf(obj));
            return this.decompress(decodedBytes);
        } catch (Exception e) {
            throw new TransformationException("Error in while reading input object as bytes");
        }
    }

}
