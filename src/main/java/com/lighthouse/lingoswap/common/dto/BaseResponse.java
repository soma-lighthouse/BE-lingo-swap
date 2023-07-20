package com.lighthouse.lingoswap.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseResponse<T> {

    private final LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private final String code;
    private final String message;
    private final T data;
}
