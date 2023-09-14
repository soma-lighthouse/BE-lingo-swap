package com.lighthouse.lingoswap.auth.dto;

public record TokenPairResponse(String accessToken,
                                Long expiresIn,
                                String refreshToken,
                                Long refreshTokenExpiresIn) {

    public static TokenPairResponse of(final String accessToken, final Long expiresIn, final String refreshToken, final Long refreshTokenExpiresIn) {
        return new TokenPairResponse(accessToken, convertToSeconds(expiresIn), refreshToken, convertToSeconds(refreshTokenExpiresIn));
    }

    private static Long convertToSeconds(final Long milliseconds) {
        return milliseconds != null ? milliseconds / 1000 : null;
    }
}
