package com.lighthouse.lingoswap.question.exception;

public class LikeMemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "LikeMember not found.";

    public LikeMemberNotFoundException() {
        super(MESSAGE);
    }

}
