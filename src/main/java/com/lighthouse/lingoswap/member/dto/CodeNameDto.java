package com.lighthouse.lingoswap.member.dto;

import lombok.Builder;

public record CodeNameDto(String code, String name) {

    @Builder
    public static CodeNameDto of(final String code, final String name) {
        return new CodeNameDto(code, name);
    }

}
