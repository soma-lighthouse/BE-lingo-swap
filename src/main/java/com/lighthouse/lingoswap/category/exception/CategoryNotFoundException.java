package com.lighthouse.lingoswap.category.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find category";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

}
