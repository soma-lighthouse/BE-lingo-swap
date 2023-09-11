package com.lighthouse.lingoswap.auth.exception;

public class InvalidTokenFormatException extends JwtException {

    private static final String MESSAGE = "Invalid token format";

    public InvalidTokenFormatException() {
        super(MESSAGE);
    }
}
