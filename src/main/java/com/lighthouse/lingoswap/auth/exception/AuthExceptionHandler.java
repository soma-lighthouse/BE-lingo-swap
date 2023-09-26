package com.lighthouse.lingoswap.auth.exception;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthNotFoundException.class)
    private ResponseEntity handleAuthNotFound(final AuthNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("40400", ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity handleInvalidAccess(final AuthenticationException ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDto.error("40300", ex.getMessage()));
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity handleExpiredToken(final ExpiredTokenException ex) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDto.error("40100", ex.getMessage()));
    }
}
