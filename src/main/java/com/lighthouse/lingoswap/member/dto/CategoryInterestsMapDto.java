package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import lombok.Builder;

import java.util.List;

public record CategoryInterestsMapDto(CodeNameDto category, List<CodeNameDto> interests) {

    @Builder
    public static CategoryInterestsMapDto of(final CodeNameDto category, final List<CodeNameDto> interests) {
        return new CategoryInterestsMapDto(category, interests);
    }

}
