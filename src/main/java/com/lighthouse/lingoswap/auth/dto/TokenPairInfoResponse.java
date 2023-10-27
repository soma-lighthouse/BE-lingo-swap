package com.lighthouse.lingoswap.auth.dto;

import lombok.Builder;

import java.io.Serializable;

public record TokenPairInfoResponse(String accessToken,
                                    Long expiresIn,
                                    String refreshToken,
                                    Long refreshTokenExpiresIn) implements Serializable {

    @Builder
    public static TokenPairInfoResponse of(final String accessToken, final Long expiresIn, final String refreshToken, final Long refreshTokenExpiresIn) {
        return new TokenPairInfoResponse(accessToken, convertToSeconds(expiresIn), refreshToken, convertToSeconds(refreshTokenExpiresIn));
    }

    private static Long convertToSeconds(final Long milliseconds) {
        return milliseconds != null ? milliseconds / 1000 : null;
    }

}
