package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.auth.service.AuthService;
import com.lighthouse.lingoswap.auth.service.TokenPairService;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String LOGIN_URL = "/api/v1/auth/login/google";
    private static final String SIGNUP_URL = "/api/v1/user";
    private static final String REISSUE_URL = "/api/v1/auth/token";
    private static final String FORM_INTERESTS_URL = "/api/v1/user/form/interests";
    private static final String FORM_COUNTRY_URL = "/api/v1/user/form/country";
    private static final String FORM_LANGUAGE_URL = "/api/v1/user/form/language";
    private static final List<String> excludedUrls = List.of(LOGIN_URL, REISSUE_URL, FORM_INTERESTS_URL, FORM_COUNTRY_URL, FORM_LANGUAGE_URL);

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthService authService;
    private final TokenPairService tokenPairService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, @NotNull final HttpServletResponse response, @NotNull final FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtUtil.extractToken(request.getHeader(JwtUtil.AUTH_HEADER));
            String username = jwtUtil.parseToken(token);
            tokenPairService.validateNotExpiredByUsername(username);
            authService.generateAuthentication(username);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            jwtAuthenticationEntryPoint.commence(request, response, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String path = request.getRequestURI();
        if (excludedUrls.contains(path)) {
            return true;
        }
        return request.getMethod().equals(HttpMethod.POST.name()) && path.equals(SIGNUP_URL);
    }
}
