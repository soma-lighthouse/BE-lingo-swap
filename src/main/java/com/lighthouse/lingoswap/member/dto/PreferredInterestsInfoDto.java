package com.lighthouse.lingoswap.member.dto;

import lombok.Builder;

import java.util.List;

public record PreferredInterestsInfoDto(String category, List<String> interests) {

    @Builder
    public static PreferredInterestsInfoDto of(final String category, final List<String> interests) {
        return new PreferredInterestsInfoDto(category, interests);
    }

}
