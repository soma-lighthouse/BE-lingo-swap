package com.lighthouse.lingoswap.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

//    @ExceptionHandler({UserNotFoundException.class})
//    public ResponseEntity<ResponseDto> handleMemberNotFound(final UserNotFoundException ex, final HttpServletRequest request) {
//        log.error("{}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(Response);
//    }
}
