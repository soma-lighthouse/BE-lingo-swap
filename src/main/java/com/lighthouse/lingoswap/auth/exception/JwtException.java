package com.lighthouse.lingoswap.auth.exception;

import org.springframework.security.core.AuthenticationException;

public abstract class JwtException extends AuthenticationException {

    protected JwtException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected JwtException(final String message) {
        super(message);
    }

}
