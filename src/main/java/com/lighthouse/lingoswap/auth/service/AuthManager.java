package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairResponse;
import com.lighthouse.lingoswap.auth.entity.TokenPair;
import com.lighthouse.lingoswap.auth.util.GoogleOAuthUtil;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import com.lighthouse.lingoswap.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthManager {

    private final AuthService authService;
    private final TokenPairService tokenPairService;
    private final JwtUtil jwtUtil;
    private final GoogleOAuthUtil googleOAuthUtil;

    @Transactional
    public ResponseDto<TokenPairResponse> login(final String idTokenValue) {
        String idToken = jwtUtil.extractToken(idTokenValue);
        String username = googleOAuthUtil.parseIdToken(idToken);

        authService.generateAuthentication(username);

        expireIfExistsByUsername(username);

        long now = System.currentTimeMillis();
        String accessToken = jwtUtil.generateToken(username, now);
        String refreshToken = jwtUtil.generateRefreshToken(username, now);

        TokenPair tokenPair = new TokenPair(username, accessToken, refreshToken);
        tokenPairService.save(tokenPair);
        return ResponseDto.success(TokenPairResponse.of(username, accessToken, now + jwtUtil.getAccessExp(), refreshToken, now + jwtUtil.getRefreshExp()));
    }

    private void expireIfExistsByUsername(final String username) {
        tokenPairService.findNotExpiredByUsername(username)
                .ifPresent(t -> {
                    t.expire();
                    tokenPairService.save(t);
                });
    }

    @Transactional
    public ResponseDto<TokenPairResponse> reissue(final ReissueRequest reissueRequest) {
        String oldRefreshToken = reissueRequest.refreshToken();
        String username = jwtUtil.parseToken(oldRefreshToken);

        authService.generateAuthentication(username);

        TokenPair oldTokenPair = tokenPairService.findNotExpiredByRefreshToken(oldRefreshToken);
        oldTokenPair.expire();
        tokenPairService.save(oldTokenPair);

        long now = System.currentTimeMillis();
        String newAccessToken = jwtUtil.generateToken(username, now);
        if (jwtUtil.isExpiredSoon(oldRefreshToken)) {
            String newRefreshToken = jwtUtil.generateRefreshToken(username, now);
            TokenPair newTokenPair = new TokenPair(username, newAccessToken, newRefreshToken);
            tokenPairService.save(newTokenPair);
            return ResponseDto.success(TokenPairResponse.of(username, newAccessToken, now + jwtUtil.getAccessExp(), newRefreshToken, now + jwtUtil.getRefreshExp()));
        } else {
            TokenPair newTokenPair = new TokenPair(username, newAccessToken, oldRefreshToken);
            tokenPairService.save(newTokenPair);
            return ResponseDto.success(TokenPairResponse.of(username, newAccessToken, now + jwtUtil.getAccessExp(), null, null));
        }
    }

    @Transactional
    public ResponseDto<Object> logout(final String accessTokenValue) {
        String accessToken = jwtUtil.extractToken(accessTokenValue);
        tokenPairService.findNotExpiredByAccessToken(accessToken).ifPresent(t -> {
            t.expire();
            tokenPairService.save(t);
        });
        SecurityContextHolder.clearContext();
        return ResponseDto.success(null);
    }
}
