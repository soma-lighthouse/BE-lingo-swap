package com.lighthouse.lingoswap.member.exception;

public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find user";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

}
