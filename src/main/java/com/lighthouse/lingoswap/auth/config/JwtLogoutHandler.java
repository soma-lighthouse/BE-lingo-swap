package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.auth.service.TokenService;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtLogoutHandler implements LogoutSuccessHandler {

    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
        String token = jwtUtil.extract(request.getHeader(JwtUtil.AUTH_HEADER));
        String username = jwtUtil.parse(token);
        tokenService.deleteAllByUsername(username);
        SecurityContextHolder.clearContext();
    }
}
