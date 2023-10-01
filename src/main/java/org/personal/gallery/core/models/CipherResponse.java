package org.personal.gallery.core.models;

import org.personal.gallery.utils.SizeCalculator;

public record CipherResponse( Long size,String payload) {
    public static CipherResponse from(String transformedResult) {

        return new CipherResponse( SizeCalculator.getObjectSize(transformedResult),transformedResult);
    }
}
