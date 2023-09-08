package com.lighthouse.lingoswap.auth.service;

import com.lighthouse.lingoswap.auth.entity.Token;
import com.lighthouse.lingoswap.auth.exception.InvalidTokenException;
import com.lighthouse.lingoswap.auth.repoistory.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    Token findByRefreshToken(final String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidTokenException::new);
    }

    void save(final Token token) {
        try {
            tokenRepository.save(token);
        } catch (DataIntegrityViolationException ex) {
            Token foundToken = findByRefreshToken(token.getRefreshToken());
            foundToken.changeRefreshToken(token.getRefreshToken());
        }
    }

    @Transactional
    public void deleteAllByUsername(final String username) {
        tokenRepository.deleteAllByUsername(username);
    }
}
