package org.personal.gallery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidRequestException.class})
    ResponseEntity<ErrorMessage> catchException(InvalidRequestException exception){
        return new ResponseEntity<>(ErrorMessage.from(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
