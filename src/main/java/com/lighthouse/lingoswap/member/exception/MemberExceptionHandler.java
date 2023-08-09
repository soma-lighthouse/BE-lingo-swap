package com.lighthouse.lingoswap.member.exception;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleMemberNotFound(final MemberNotFoundException ex, final HttpServletRequest request) {
        log.error("MemberNotFoundException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.builder()
                        .code("40000")
                        .message(ex.getMessage())
                        .build()
                );
    }
}
