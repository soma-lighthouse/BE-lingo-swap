package com.lighthouse.lingoswap.member.exception;

public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find user with id: %d";

    public MemberNotFoundException(final Long memberId) {
        super(MESSAGE.formatted(memberId));
    }
}
