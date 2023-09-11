package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.entity.TokenPair;
import com.lighthouse.lingoswap.auth.exception.InvalidTokenException;
import com.lighthouse.lingoswap.auth.repoistory.TokenPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenPairService {

    private final TokenPairRepository tokenPairRepository;

    public Optional<TokenPair> findNotExpiredByUsername(final String username) {
        return tokenPairRepository.findTopByUsernameAndIsExpiredIsFalse(username);
    }

    void save(final TokenPair token) {
        tokenPairRepository.save(token);
    }

    TokenPair findNotExpiredByRefreshToken(final String refreshToken) {
        return tokenPairRepository.findTopByRefreshTokenAndIsExpiredIsFalse(refreshToken)
                .orElseThrow(InvalidTokenException::new);
    }

    public Optional<TokenPair> findNotExpiredByAccessToken(final String accessToken) {
        return tokenPairRepository.findTopByAccessTokenAndIsExpiredIsFalse(accessToken);
    }

    public void validateNotExpiredByUsername(final String username) {
        if (!tokenPairRepository.existsByUsernameAndIsExpiredIsFalse(username)) {
            throw new InvalidTokenException();
        }
    }
}
