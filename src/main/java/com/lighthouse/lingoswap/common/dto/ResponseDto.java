package com.lighthouse.lingoswap.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ResponseDto<T> {

    private final LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private final String code;
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
