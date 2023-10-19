package com.lighthouse.lingoswap.country.exception;

public class CountryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find user";

    public CountryNotFoundException() {
        super(MESSAGE);
    }

}
