package com.lighthouse.lingoswap.auth.application;

import com.lighthouse.lingoswap.auth.domain.model.TokenPair;
import com.lighthouse.lingoswap.auth.domain.repoistory.TokenPairRepository;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.auth.exception.InvalidTokenException;
import com.lighthouse.lingoswap.auth.exception.InvalidUserException;
import com.lighthouse.lingoswap.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenPairService {

    private final TokenPairRepository tokenPairRepository;
    private final JwtUtil jwtUtil;

    TokenPairInfoResponse generateTokenPairDetailsByUsername(final String username) {
        expireIfExistsByUsername(username);

        long now = System.currentTimeMillis();
        String accessToken = jwtUtil.generateToken(username, now);
        String refreshToken = jwtUtil.generateRefreshToken(username, now);

        TokenPair tokenPair = new TokenPair(username, accessToken, refreshToken);
        tokenPairRepository.save(tokenPair);
        return TokenPairInfoResponse.of(accessToken, now + jwtUtil.getAccessExp(), refreshToken, now + jwtUtil.getRefreshExp());
    }

    public void expireIfExistsByUsername(final String username) {
        tokenPairRepository.findAllByUsernameAndIsExpiredIsFalse(username)
                .forEach(TokenPair::expire);
    }

    TokenPairInfoResponse reissue(final String oldRefreshToken) {
        String email = parseToken(oldRefreshToken);

        TokenPair oldTokenPair = findNotExpiredByRefreshToken(oldRefreshToken);
        oldTokenPair.expire();

        long now = System.currentTimeMillis();
        String newAccessToken = jwtUtil.generateToken(email, now);
        if (jwtUtil.isExpiredSoon(oldRefreshToken)) {
            String newRefreshToken = jwtUtil.generateRefreshToken(email, now);
            TokenPair newTokenPair = new TokenPair(email, newAccessToken, newRefreshToken);
            tokenPairRepository.save(newTokenPair);
            return TokenPairInfoResponse.of(newAccessToken, now + jwtUtil.getAccessExp(), newRefreshToken, now + jwtUtil.getRefreshExp());
        } else {
            TokenPair newTokenPair = new TokenPair(email, newAccessToken, oldRefreshToken);
            tokenPairRepository.save(newTokenPair);
            return TokenPairInfoResponse.of(newAccessToken, now + jwtUtil.getAccessExp(), null, null);
        }
    }

    public String parseToken(final String tokenString) {
        return jwtUtil.parseToken(tokenString);
    }

    private TokenPair findNotExpiredByRefreshToken(final String refreshToken) {
        return tokenPairRepository.findTopByRefreshTokenAndIsExpiredIsFalse(refreshToken)
                .orElseThrow(InvalidTokenException::new);
    }

    public TokenPair findNotExpiredByUsername(final String username) {
        return tokenPairRepository.findTopByUsernameAndIsExpiredIsFalse(username)
                .orElseThrow(() -> new InvalidUserException(username));
    }

}
