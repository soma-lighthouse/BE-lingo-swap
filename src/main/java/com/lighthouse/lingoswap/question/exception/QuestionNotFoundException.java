package com.lighthouse.lingoswap.question.exception;

public class QuestionNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Couldn't find question.";

    public QuestionNotFoundException() {
        super(MESSAGE);
    }

}
