package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record MemberProfileResponse(String uuid,
                                    String profileImageUrl,
                                    String name,
                                    int age,
                                    String description,
                                    CodeNameDto region,
                                    List<CodeNameDto> preferredCountries,
                                    List<UsedLanguageDto> usedLanguages,
                                    List<CategoryInterestsMapDto> preferredInterests) {

    @Builder
    public static MemberProfileResponse of(final Member member,
                                           final String profileImageUrl,
                                           final LocalDate currentDate,
                                           final CodeNameDto region,
                                           final List<CodeNameDto> preferredCountries,
                                           final List<UsedLanguage> usedLanguages,
                                           final List<CategoryInterestsMapDto> preferredInterests) {
        return new MemberProfileResponse(
                member.getUuid(),
                profileImageUrl,
                member.getName(),
                member.calculateAge(currentDate),
                member.getDescription(),
                region,
                preferredCountries,
                usedLanguages.stream().map(UsedLanguageDto::from).toList(),
                preferredInterests
        );
    }

}
