package com.lighthouse.lingoswap.auth.dto;

import lombok.Builder;

public record ReissueRequest(String refreshToken) {

    @Builder
    public static ReissueRequest from(final String refreshToken) {
        return new ReissueRequest(refreshToken);
    }

}
