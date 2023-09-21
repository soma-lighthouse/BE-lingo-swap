package com.lighthouse.lingoswap.auth.dto;

import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfo;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfo;
import com.lighthouse.lingoswap.member.entity.Gender;

import java.time.LocalDate;
import java.util.List;

public record MemberCreateRequest(String uuid,
                                  String profileImageUri,
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
