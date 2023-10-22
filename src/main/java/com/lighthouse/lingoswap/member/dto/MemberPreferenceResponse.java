package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.Builder;

import java.util.List;

public record MemberPreferenceResponse(List<CodeNameDto> preferredCountries,
                                       List<UsedLanguageDto> usedLanguages,
                                       List<CategoryInterestsMapDto> preferredInterests) {

    @Builder
    public static MemberPreferenceResponse of(final List<CodeNameDto> preferredCountries,
                                              final List<UsedLanguage> usedLanguages,
                                              final List<CategoryInterestsMapDto> preferredInterests) {
        return new MemberPreferenceResponse(
                preferredCountries,
                usedLanguages.stream().map(UsedLanguageDto::from).toList(),
                preferredInterests
        );
    }

}
