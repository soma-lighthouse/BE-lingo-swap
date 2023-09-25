package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberProfileResponse(String uuid,
                                    String profileImageUri,
                                    String name,
                                    int age,
                                    String description,
                                    String region,
                                    List<CodeNameDto> preferredCountries,
                                    List<UsedLanguageDto> usedLanguages,
                                    List<MemberPreferredInterests> preferredInterests) {

}
