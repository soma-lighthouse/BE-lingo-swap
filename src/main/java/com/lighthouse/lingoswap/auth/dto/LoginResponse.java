package com.lighthouse.lingoswap.auth.dto;

public record LoginResponse(String uuid, TokenPairResponse tokens) {

    public static LoginResponse of(final String uuid, final TokenPairResponse tokens) {
        return new LoginResponse(uuid, tokens);
    }
}
