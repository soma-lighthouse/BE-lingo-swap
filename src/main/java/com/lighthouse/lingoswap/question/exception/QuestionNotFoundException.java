package com.lighthouse.lingoswap.question.exception;

public class QuestionNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Question not found. ID: %d";

    public QuestionNotFoundException(final Long id) {
        super(MESSAGE.formatted(id));
    }
}
