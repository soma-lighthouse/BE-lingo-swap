package com.lighthouse.lingoswap.auth.repoistory;

import com.lighthouse.lingoswap.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUsername(String username);

    Optional<Token> findByRefreshToken(String refreshToken);

    void deleteAllByUsername(String username);
}
