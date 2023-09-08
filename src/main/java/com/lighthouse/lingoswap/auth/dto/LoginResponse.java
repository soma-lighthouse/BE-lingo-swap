package com.lighthouse.lingoswap.auth.dto;

public record LoginResponse(String username,
                            String accessToken,
                            long expiresIn,
                            String refreshToken,
                            long refreshTokenExpiresIn) {

    public static LoginResponse of(final String username, final String accessToken, final long expiresIn, final String refreshToken, final long refreshTokenExpiresIn) {
        return new LoginResponse(username, accessToken, expiresIn, refreshToken, refreshTokenExpiresIn);
    }
}
