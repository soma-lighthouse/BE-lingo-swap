package com.lighthouse.lingoswap.auth.exception;

public class ExpiredTokenException extends JwtException {

    private static final String MESSAGE = "Your token is expired.";

    public ExpiredTokenException(Throwable reason) {
        super(MESSAGE, reason);
    }

}
