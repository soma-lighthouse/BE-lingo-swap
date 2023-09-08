package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.auth.service.AuthManager;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String LOGIN_URL = "/api/v1/auth/login";
    private static final String REISSUE_URL = "/api/v1/auth/token";

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthManager authManager;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, @NotNull final HttpServletResponse response, @NotNull final FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtUtil.extract(request.getHeader(JwtUtil.AUTH_HEADER));
            String username = jwtUtil.parse(token);
            Authentication auth = authManager.generateAuthentication(username);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            jwtAuthenticationEntryPoint.commence(request, response, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals(LOGIN_URL) || path.equals(REISSUE_URL);
    }
}
