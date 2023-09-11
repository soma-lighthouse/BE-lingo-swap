package com.lighthouse.lingoswap.member.exception;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    private ResponseEntity handleMemberNotFound(final MemberNotFoundException ex) {
        log.error("MemberNotFoundException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("40000", ex.getMessage()));
    }
}
