package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequest {

    private String profileImageUri;
    private LocalDate birthday;
    private String name;
    private String email;
    private Gender gender;
    private Integer age;
    private String description;
    private String region;
    private List<String> preferredCountries;
    private List<UsedLanguage> usedLanguages;
    private List<PreferredInterests> preferredInterests;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UsedLanguage {

        private String code;
        private Integer level;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PreferredInterests {

        private String category;
        private List<String> interests;
    }
}
