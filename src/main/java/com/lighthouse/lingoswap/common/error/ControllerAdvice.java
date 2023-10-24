package com.lighthouse.lingoswap.common.error;

import com.lighthouse.lingoswap.common.dto.ErrorMessage;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.service.ErrorResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.lighthouse.lingoswap.common.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Order
@RestControllerAdvice
public class ControllerAdvice {

    private final ErrorResponseService errorResponseService;

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto<ErrorMessage>> handleUnexpectedException(final Exception ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.buildResponse(null, SERVER_ERROR);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
    })
    private ResponseEntity<ResponseDto<ErrorMessage>> handleForbiddenException(final Exception ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.buildResponse(null, FORBIDDEN_ERROR);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    private ResponseEntity<ResponseDto<ErrorMessage>> handleMethodArgumentNotValidException(final Exception ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.buildResponse(null, VALIDATION_ERROR);
    }

    @ExceptionHandler(BindException.class)
    private ResponseEntity<ResponseDto<ErrorMessage>> handleBindException(final BindException ex) {
        log.error("{}", ex.getAllErrors().get(0));
        return errorResponseService.buildResponse(ex.getAllErrors().get(0).getDefaultMessage(), VALIDATION_ERROR);
    }

}
