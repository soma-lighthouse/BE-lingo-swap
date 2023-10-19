package com.lighthouse.lingoswap.member.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberPreferenceResponse(List<CodeNameDto> preferredCountries,
                                       List<UsedLanguageDto> usedLanguages,
                                       List<CategoryInterestsMapDto> preferredInterests) {

}
