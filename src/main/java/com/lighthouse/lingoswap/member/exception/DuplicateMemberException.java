package com.lighthouse.lingoswap.member.exception;

public class DuplicateMemberException extends RuntimeException {

    private static final String MESSAGE = "Duplicate member: %s";

    public DuplicateMemberException(final String username, final Throwable cause) {
        super(MESSAGE.formatted(username), cause);
    }
}
