package com.lighthouse.lingoswap.member.dto;

import lombok.Builder;

import java.util.List;

public record MemberPreferenceRequest(List<String> preferredCountries,
                                      List<UsedLanguageInfoDto> usedLanguages,
                                      List<PreferredInterestsInfoDto> preferredInterests) {

    @Builder
    public static MemberPreferenceRequest of(final List<String> preferredCountries,
                                             final List<UsedLanguageInfoDto> usedLanguages,
                                             final List<PreferredInterestsInfoDto> preferredInterests) {
        return new MemberPreferenceRequest(preferredCountries, usedLanguages, preferredInterests);
    }

}
