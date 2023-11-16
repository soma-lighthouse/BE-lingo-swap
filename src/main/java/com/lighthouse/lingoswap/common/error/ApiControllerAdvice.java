package com.lighthouse.lingoswap.common.error;

import com.lighthouse.lingoswap.category.exception.CategoryNotFoundException;
import com.lighthouse.lingoswap.common.dto.ErrorMessage;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.service.ErrorResponseService;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeMemberException;
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
    private ResponseEntity<ResponseDto<ErrorMessage>> handleAuthenticationException(final AuthenticationException ex) {
        log.warn("{}", ex.getMessage());
        return errorResponseService.buildResponse(ex.getMessage(), FORBIDDEN_ERROR);
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            QuestionNotFoundException.class,
            CategoryNotFoundException.class
    })
    private ResponseEntity<ResponseDto<ErrorMessage>> handleNotFoundException(final RuntimeException ex) {
        log.warn("{}", ex.getMessage());
        return errorResponseService.buildResponse(ex.getMessage(), NOT_FOUND_ERROR);
    }

    @ExceptionHandler({
            DuplicateLikeMemberException.class,
            LikeMemberNotFoundException.class
    })
    private ResponseEntity<ResponseDto<ErrorMessage>> handleDuplicateLikeException(final RuntimeException ex) {
        log.warn("{}", ex.getMessage());
        return errorResponseService.buildResponse(ex.getMessage(), VALIDATION_ERROR);
    }

}
