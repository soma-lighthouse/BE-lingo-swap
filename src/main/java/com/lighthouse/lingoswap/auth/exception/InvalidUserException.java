package com.lighthouse.lingoswap.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserException extends AuthenticationException {

    private static final String MESSAGE = "Invalid user: %s";

    public InvalidUserException(final String username) {
        super(MESSAGE.formatted(username));
    }

}
