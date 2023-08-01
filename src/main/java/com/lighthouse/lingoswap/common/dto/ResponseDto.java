package com.lighthouse.lingoswap.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Builder
@Getter
public class ResponseDto<T> {

    private final LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private final Integer code;
    private final String message;
    private final T data;
}
