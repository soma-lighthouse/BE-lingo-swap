package com.lighthouse.lingoswap.auth.dto;

public record TokenPairResponse(String username,
                                String accessToken,
                                Long expiresIn,
                                String refreshToken,
                                Long refreshTokenExpiresIn) {

    public static TokenPairResponse of(final String username, final String accessToken, final Long expiresIn, final String refreshToken, final Long refreshTokenExpiresIn) {
        return new TokenPairResponse(username, accessToken, convertToSeconds(expiresIn), refreshToken, convertToSeconds(refreshTokenExpiresIn));
    }

    private static Long convertToSeconds(Long milliseconds) {
        return milliseconds / 1000;
    }
}
