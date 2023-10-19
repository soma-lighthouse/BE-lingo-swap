package com.lighthouse.lingoswap.auth.dto;

public record LoginResponse(String uuid, String username, TokenPairInfoResponse tokens) {

    public static LoginResponse of(final String uuid, final String username, final TokenPairInfoResponse tokens) {
        return new LoginResponse(uuid, username, tokens);
    }

}
