package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;

import java.util.List;

public record MemberSimpleProfile(String uuid,
                                  String profileImageUri,
                                  String name,
                                  String description,
                                  CodeNameDto region,
                                  List<UsedLanguageDto> usedLanguages) {

    public static MemberSimpleProfile of(final Member member,
                                         final String profileImageUri,
                                         final CodeNameDto region,
                                         final List<UsedLanguage> usedLanguages) {
        return new MemberSimpleProfile(
                member.getUuid(),
                profileImageUri,
                member.getName(),
                member.getDescription(),
                region,
                usedLanguages.stream().map(UsedLanguageDto::from).toList()
        );
    }

}
