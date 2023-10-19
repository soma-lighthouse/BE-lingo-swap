package com.lighthouse.lingoswap.common.dto;

public record ErrorMessage(String message) {

    public static ErrorMessage of(final String message) {
        return new ErrorMessage(message);
    }

}
