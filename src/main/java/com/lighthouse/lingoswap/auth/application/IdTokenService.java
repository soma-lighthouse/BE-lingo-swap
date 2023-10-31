package com.lighthouse.lingoswap.auth.application;

public interface IdTokenService {

    String parseIdToken(String idTokenString);

}
