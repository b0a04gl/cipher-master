package org.personal.gallery.utils;

public enum ApplicationConstants {

    LZMA("LZMA"),
    ZSTD("ZSTD"),
    GZIP("GZIP"),
    LIMO_ZSTD("LIMO_ZSTD"),
    ZSTD_Proto("ZSTD_Proto"),
    GZIP_Proto("GZIP_Proto");


    public final String value;

    public String getValue() {
        return value;
    }

    private ApplicationConstants(String value) {
        this.value = value;
    }
}
