package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferenceResponse(List<CountryFormResponseUnit> preferredCountries, List<String> usedLanguages,
                                       List<InterestsWithCategoryUnit> preferredInterests) {

}
