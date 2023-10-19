package com.lighthouse.lingoswap.interests.exception;

public class InterestsNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Couldn't find Interests";

    public InterestsNotFoundException() {
        super(MESSAGE);
    }

}
