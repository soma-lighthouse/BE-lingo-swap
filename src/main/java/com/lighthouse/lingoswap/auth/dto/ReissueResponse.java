package com.lighthouse.lingoswap.auth.dto;

public record ReissueResponse(String username,
                              String accessToken,
                              long expiresIn,
                              String refreshToken,
                              Long refreshTokenExpiresIn) {

    public static ReissueResponse of(final String username, final String accessToken, final long expiresIn, final String refreshToken, final Long refreshTokenExpiresIn) {
        return new ReissueResponse(username, accessToken, expiresIn, refreshToken, refreshTokenExpiresIn);
    }
}
