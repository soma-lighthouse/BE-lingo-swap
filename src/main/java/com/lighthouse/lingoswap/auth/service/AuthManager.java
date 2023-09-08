package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.dto.LoginRequest;
import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueResponse;
import com.lighthouse.lingoswap.auth.entity.Token;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthManager {

    private final AuthService authService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    public ResponseDto<LoginResponse> login(final LoginRequest loginRequest) {
        String username = loginRequest.email();

        Authentication auth = generateAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtUtil.generate(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        Token token = new Token(username, refreshToken);
        tokenService.save(token);
        return ResponseDto.success(LoginResponse.of(username, accessToken, jwtUtil.getAccessExp(), refreshToken, jwtUtil.getRefreshExp()));
    }

    public Authentication generateAuthentication(final String username) {
        UserDetails userDetails = authService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public ResponseDto<ReissueResponse> reissue(final ReissueRequest reissueRequest) {
        Token token = tokenService.findByRefreshToken(reissueRequest.refreshToken());

        String username = jwtUtil.parse(token.getRefreshToken());
        Authentication auth = generateAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtUtil.generate(username);
        if (jwtUtil.isExpiredSoon(token.getRefreshToken())) {
            String refreshToken = jwtUtil.generateRefreshToken(username);
            return ResponseDto.success(ReissueResponse.of(username, accessToken, jwtUtil.getAccessExp(), refreshToken, jwtUtil.getRefreshExp()));
        }
        return ResponseDto.success(ReissueResponse.of(username, accessToken, jwtUtil.getAccessExp(), null, null));
    }
}
