package com.lighthouse.lingoswap.question.exception;

public class DuplicateLikeException extends RuntimeException {

    private static final String MESSAGE = "Already liked this question";

    public DuplicateLikeException() {
        super(MESSAGE);
    }
}
