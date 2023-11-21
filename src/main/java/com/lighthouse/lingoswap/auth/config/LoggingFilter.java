package com.lighthouse.lingoswap.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(@NotNull final HttpServletRequest request,
                                    @NotNull final HttpServletResponse response,
                                    @NotNull final FilterChain filterChain) throws ServletException, IOException {
        LogDto logDto = LogDto.from(request);
        log.info("{}", logDto);
        filterChain.doFilter(request, response);
    }

    private record LogDto(String method,
                          String uri,
                          String queryString,
                          String realIp,
                          String region,
                          String userAgent,
                          String acceptLanguage) {

        static LogDto from(HttpServletRequest request) {
            return new LogDto(
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getQueryString(),
                    request.getHeader("X-Real-IP"),
                    request.getHeader("Region"),
                    request.getHeader("User-Agent"),
                    request.getHeader("Accept-Language"));
        }

        @Override
        public String toString() {
            return """
                                        
                    [REQUEST]
                    %s %s%s
                    headers=[X-Real-IP: %s, Region: %s, User-Agent: %s, Accept-Language: %s]
                    """
                    .formatted(
                            method,
                            uri,
                            Optional.ofNullable(queryString).map(q -> "?" + q).orElse(""),
                            realIp,
                            region,
                            userAgent,
                            acceptLanguage);
        }

    }

}
