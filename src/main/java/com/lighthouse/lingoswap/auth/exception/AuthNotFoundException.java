package com.lighthouse.lingoswap.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthNotFoundException extends AuthenticationException {

    private static final String MESSAGE = "Couldn't find user with username: %s";

    public AuthNotFoundException(final String username) {
        super(MESSAGE.formatted(username));
    }
}
