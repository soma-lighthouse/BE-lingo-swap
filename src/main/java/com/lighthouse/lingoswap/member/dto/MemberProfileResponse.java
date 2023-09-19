package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberProfileResponse(Long id,
                                    String profileImageUri,
                                    String name,
                                    int age,
                                    String description,
                                    String region,
                                    List<CountryFormResponseUnit> preferredCountries,
                                    List<MemberUsedLanguage> usedLanguages,
                                    List<MemberPreferredInterests> preferredInterests) {

}
