package com.lighthouse.lingoswap.country.domain.repository;

public class CountryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find user";

    public CountryNotFoundException() {
        super(MESSAGE);
    }

}
