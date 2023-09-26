package com.lighthouse.lingoswap.auth.dto;

public record LoginResponse(String uuid, String username, TokenPairDetails tokens) {

    public static LoginResponse of(final String uuid, final String username, final TokenPairDetails tokens) {
        return new LoginResponse(uuid, username, tokens);
    }
}
