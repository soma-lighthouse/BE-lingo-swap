package com.lighthouse.lingoswap.common.exception;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionController {

    @ExceptionHandler
    private ResponseEntity handleDtoNotValid(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("40000", ex.getMessage()));
    }
}