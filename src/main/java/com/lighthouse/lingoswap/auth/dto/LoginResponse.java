package com.lighthouse.lingoswap.auth.dto;

public record LoginResponse(String uuid, TokenPairDetails tokens) {

    public static LoginResponse of(final String uuid, final TokenPairDetails tokens) {
        return new LoginResponse(uuid, tokens);
    }
}
