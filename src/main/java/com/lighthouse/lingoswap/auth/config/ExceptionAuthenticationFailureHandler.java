package com.lighthouse.lingoswap.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lighthouse.lingoswap.auth.exception.ExpiredTokenException;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExceptionAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException ex) throws IOException {
        log.error("", ex);

        HttpStatus status;
        String code;
        if (ex instanceof ExpiredTokenException) {
            status = HttpStatus.UNAUTHORIZED;
            code = "40100";
        } else {
            status = HttpStatus.FORBIDDEN;
            code = "40300";
        }
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResponseDto.error(code, ex.getMessage())));
    }
}
