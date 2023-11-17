package com.lighthouse.lingoswap.question.exception;

public class DuplicateLikeMemberException extends RuntimeException {

    private static final String MESSAGE = "Already liked this question";

    public DuplicateLikeMemberException() {
        super(MESSAGE);
    }

}
