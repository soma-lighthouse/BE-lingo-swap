package com.lighthouse.lingoswap.auth.dto;

import com.lighthouse.lingoswap.member.domain.model.Gender;
import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfoDto;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfoDto;

import java.time.LocalDate;
import java.util.List;

public record MemberCreateRequest(String uuid,
                                  String profileImageUrl,
                                  LocalDate birthday,
                                  String name,
                                  String email,
                                  Gender gender,
                                  Integer age,
                                  String description,
                                  String region,
                                  List<String> preferredCountries,
                                  List<UsedLanguageInfoDto> usedLanguages,
                                  List<PreferredInterestsInfoDto> preferredInterests) {

}
