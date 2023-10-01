package org.personal.gallery.application;

import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.exceptions.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;



@Component
public class RequestValidator {

    private Logger logger = LoggerFactory.getLogger(RequestValidator.class);

    public boolean validateRequest(CipherRequest request){
        logger.info("payload validation : {},",request);
        System.out.println("hi ::: "+request.compressionType()+","+request.payload());
         if(ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.compressionType()) || ObjectUtils.isEmpty(request.payload())){
             Object payload = request.payload();
             throw new InvalidRequestException("Request is missing some mandatory fields!");
         }
         return true;
    }
}
