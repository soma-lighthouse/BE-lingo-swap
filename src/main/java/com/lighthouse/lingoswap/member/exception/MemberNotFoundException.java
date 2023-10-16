package com.lighthouse.lingoswap.member.exception;

public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find user with uuid: %s";

    public MemberNotFoundException(final String uuid) {
        super(MESSAGE.formatted(uuid));
    }

}
