package com.lighthouse.lingoswap.auth.dto;

import lombok.Builder;

public record LoginResponse(String uuid, String username, TokenPairInfoResponse tokens) {

    @Builder
    public static LoginResponse of(final String uuid, final String username, final TokenPairInfoResponse tokens) {
        return new LoginResponse(uuid, username, tokens);
    }

}
