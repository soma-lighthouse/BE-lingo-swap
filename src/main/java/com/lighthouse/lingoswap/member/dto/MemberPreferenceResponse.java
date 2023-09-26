package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferenceResponse(List<CodeNameDto> preferredCountries,
                                       List<UsedLanguageDto> usedLanguages,
                                       List<MemberPreferredInterests> preferredInterests) {

}
