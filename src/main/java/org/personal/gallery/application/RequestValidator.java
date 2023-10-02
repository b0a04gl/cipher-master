package org.personal.gallery.application;

import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.exceptions.InvalidRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
public class RequestValidator {

    public boolean validateRequest(CipherRequest request) {
        if (ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.compressionType()) || ObjectUtils.isEmpty(request.payload())) {
            throw new InvalidRequestException("Request is missing some mandatory fields!");
        }
        return true;
    }
}
