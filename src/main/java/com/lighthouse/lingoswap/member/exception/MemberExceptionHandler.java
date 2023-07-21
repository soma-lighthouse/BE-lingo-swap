package com.lighthouse.lingoswap.member.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

//    @ExceptionHandler({MemberNotFoundException.class})
//    public ResponseEntity<ResponseDto> handleMemberNotFound(final MemberNotFoundException ex, final HttpServletRequest request) {
//        log.error("{}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(Response);
//    }
}
