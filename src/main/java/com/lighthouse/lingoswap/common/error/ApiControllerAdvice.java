package com.lighthouse.lingoswap.common.error;

import com.lighthouse.lingoswap.common.service.ErrorResponseService;
import com.lighthouse.lingoswap.member.exception.DuplicateMemberException;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeException;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import com.lighthouse.lingoswap.question.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lighthouse.lingoswap.common.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiControllerAdvice {

    private final ErrorResponseService errorResponseService;

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity handleAuthenticationException(final RuntimeException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(FORBIDDEN_ERROR);
    }

    @ExceptionHandler(DuplicateMemberException.class)
    private ResponseEntity handleDuplicateMemberException(final RuntimeException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(DUPLICATE_USER_ERROR);
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            QuestionNotFoundException.class
    })
    private ResponseEntity handleLoginUserNotFoundException(final RuntimeException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(NOT_FOUND_ERROR);
    }

    @ExceptionHandler({
            DuplicateLikeException.class,
            LikeMemberNotFoundException.class
    })
    private ResponseEntity handleDuplicateLikeException(final RuntimeException ex) {
        log.error("{}", ex.getMessage());
        return errorResponseService.build(VALIDATION_ERROR);
    }
}
