package org.personal.gallery.core.ports;

import org.personal.gallery.core.models.CipherRequest;
import org.personal.gallery.core.models.CipherResponse;

public interface CipherService {
    CipherResponse handleRequest(CipherRequest cipherRequest, int operationCode) throws Exception;
}
