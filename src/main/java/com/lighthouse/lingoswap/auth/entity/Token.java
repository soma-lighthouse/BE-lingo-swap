package com.lighthouse.lingoswap.auth.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Token extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String refreshToken;

    public Token(final String username, final String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public void changeRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
