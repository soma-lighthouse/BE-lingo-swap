package com.lighthouse.lingoswap.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

    public static final String SUCCESS_CODE = "20000";
    public static final String SUCCESS_MESSAGE = "Request sent successfully";

    private final LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private final String code;
    private final String message;
    private final T data;

    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static ResponseDto<Void> noData() {
        return new ResponseDto<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static ResponseDto<ErrorMessage> error(final String code, final String message) {
        return new ResponseDto<>(code, message, null);
    }

    public static ResponseDto<ErrorMessage> error(final String code, final String message, final ErrorMessage errorMessage) {
        return new ResponseDto<>(code, message, errorMessage);
    }

}
