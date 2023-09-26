package com.lighthouse.lingoswap.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    COMMON_EXCEPTION("40300");

    private final String code;

    public String getKey() {
        return "exception.%s.message".formatted(code);
    }
}
