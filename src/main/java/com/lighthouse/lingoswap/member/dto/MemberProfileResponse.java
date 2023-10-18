package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberProfileResponse(String uuid,
                                    String profileImageUrl,
                                    String name,
                                    int age,
                                    String description,
                                    CodeNameDto region,
                                    List<CodeNameDto> preferredCountries,
                                    List<UsedLanguageDto> usedLanguages,
                                    List<MemberPreferredInterests> preferredInterests) {

}
