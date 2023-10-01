package org.personal.gallery.exceptions;

public class InvalidRequestException extends RuntimeException{
    public String errorMessage;
    public InvalidRequestException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
