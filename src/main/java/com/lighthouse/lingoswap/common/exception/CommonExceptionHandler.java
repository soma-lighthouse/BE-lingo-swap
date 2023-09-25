package com.lighthouse.lingoswap.common.exception;

import com.lighthouse.lingoswap.common.service.ErrorResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lighthouse.lingoswap.common.exception.ExceptionCode.COMMON_EXCEPTION;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExceptionHandler {

    private final ErrorResponseService errorResponseService;

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity handleCommonException(final RuntimeException ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponseService.build(COMMON_EXCEPTION));
    }
}
