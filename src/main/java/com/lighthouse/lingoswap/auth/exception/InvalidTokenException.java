package com.lighthouse.lingoswap.auth.exception;

public class InvalidTokenException extends JwtException {

    private static final String MESSAGE = "Invalid access";

    public InvalidTokenException() {
        super(MESSAGE);
    }
}
