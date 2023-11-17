package com.lighthouse.lingoswap.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidIdTokenException extends AuthenticationException {

    private static final String MESSAGE = "Invalid id token";

    public InvalidIdTokenException(final Throwable cause) {
        super(MESSAGE, cause);
    }

}
