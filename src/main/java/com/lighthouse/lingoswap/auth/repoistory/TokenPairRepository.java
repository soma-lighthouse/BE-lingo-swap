package com.lighthouse.lingoswap.auth.repoistory;

import com.lighthouse.lingoswap.auth.entity.TokenPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenPairRepository extends JpaRepository<TokenPair, Long> {

    Optional<TokenPair> findTopByUsernameAndIsExpiredIsFalse(String username);

    Optional<TokenPair> findTopByRefreshTokenAndIsExpiredIsFalse(String refreshToken);

    List<TokenPair> findAllByUsernameAndIsExpiredIsFalse(String username);

}
