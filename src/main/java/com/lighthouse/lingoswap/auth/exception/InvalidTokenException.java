package com.lighthouse.lingoswap.auth.exception;

public class InvalidTokenException extends JwtException {

    private static final String MESSAGE = "Invalid token";

    public InvalidTokenException() {
        super(MESSAGE);
    }

    public InvalidTokenException(Throwable reason) {
        super(MESSAGE, reason);
    }
}
