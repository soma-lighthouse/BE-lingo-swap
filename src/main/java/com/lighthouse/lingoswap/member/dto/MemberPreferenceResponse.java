package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferenceResponse(List<CountryFormResponseUnit> preferredCountries,
                                       List<MemberUsedLanguage> usedLanguages,
                                       List<MemberPreferredInterests> preferredInterests) {

}
