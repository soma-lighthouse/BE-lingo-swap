package com.lighthouse.lingoswap.language.exception;

public class LanguageNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Couldn't find language";

    public LanguageNotFoundException() {
        super(MESSAGE);
    }

}
