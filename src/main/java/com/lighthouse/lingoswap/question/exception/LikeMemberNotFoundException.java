package com.lighthouse.lingoswap.question.exception;

public class LikeMemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "LikeMember not found. Member: %s Question: %d";

    public LikeMemberNotFoundException(final String username, final Long questionId) {
        super(MESSAGE.formatted(username, questionId));
    }
}
