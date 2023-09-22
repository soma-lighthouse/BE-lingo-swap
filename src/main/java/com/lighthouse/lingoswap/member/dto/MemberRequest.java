package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Gender;

import java.time.LocalDate;
import java.util.List;

public record MemberRequest(String profileImageUri,
                            LocalDate birthday,
                            String name,
                            String email,
                            Gender gender,
                            Integer age,
                            String description,
                            String region,
                            List<String> preferredCountries,
                            List<UsedLanguageInfo> usedLanguages,
                            List<PreferredInterestsInfo> preferredInterests) {

}
