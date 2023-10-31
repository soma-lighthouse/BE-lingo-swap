package com.lighthouse.lingoswap.country.exception;

public class CountryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find country. %s";

    public CountryNotFoundException(final String code) {
        super(MESSAGE.formatted(code));
    }

}
