package com.lighthouse.lingoswap.common.error;

import com.lighthouse.lingoswap.common.service.ErrorResponseService;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lighthouse.lingoswap.common.error.ErrorCode.FORBIDDEN_ERROR;
import static com.lighthouse.lingoswap.common.error.ErrorCode.USER_NOT_FOUND_ERROR;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiControllerAdvice {

    private final ErrorResponseService errorResponseService;

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity handleAuthenticationException(final AuthenticationException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(FORBIDDEN_ERROR);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    private ResponseEntity handleLoginUserNotFoundException(final MemberNotFoundException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(USER_NOT_FOUND_ERROR);
    }
}
