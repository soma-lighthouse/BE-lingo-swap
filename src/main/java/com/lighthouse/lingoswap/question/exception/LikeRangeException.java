package com.lighthouse.lingoswap.question.exception;

public class LikeRangeException extends RuntimeException {

    private static final String MESSAGE = "Invalid Like value";

    public LikeRangeException() {
        super(MESSAGE);
    }

}
