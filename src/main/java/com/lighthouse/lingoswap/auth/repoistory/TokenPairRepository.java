package com.lighthouse.lingoswap.auth.repoistory;

import com.lighthouse.lingoswap.auth.entity.TokenPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenPairRepository extends JpaRepository<TokenPair, Long> {

    Optional<TokenPair> findTopByUsernameAndIsExpiredIsFalse(String username);

    Optional<TokenPair> findTopByAccessTokenAndIsExpiredIsFalse(String accessToken);

    Optional<TokenPair> findTopByRefreshTokenAndIsExpiredIsFalse(String refreshToken);

    boolean existsByUsernameAndIsExpiredIsFalse(String username);
}
