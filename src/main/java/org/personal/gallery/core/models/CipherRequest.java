package org.personal.gallery.core.models;

import org.springframework.boot.context.properties.bind.DefaultValue;

public record CipherRequest (@DefaultValue(value = "GZIP") String compressionType, Object payload){}