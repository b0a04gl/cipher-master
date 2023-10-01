package org.personal.gallery.exceptions;

public record ErrorMessage(String payload) {
    public static ErrorMessage from(String payload){
        return new ErrorMessage(payload);
    }
}
