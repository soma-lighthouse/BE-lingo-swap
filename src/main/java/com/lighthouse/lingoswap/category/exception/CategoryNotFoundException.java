package com.lighthouse.lingoswap.category.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find category with id: %d";

    public CategoryNotFoundException(final Long id) {
        super(MESSAGE.formatted(id));
    }

}
