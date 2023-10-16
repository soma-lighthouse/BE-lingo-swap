package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.domain.model.Member;

import java.util.List;

public record MemberSimpleProfile(String uuid,
                                  String profileImageUri,
                                  String name,
                                  String description,
                                  CodeNameDto region,
                                  List<UsedLanguageDto> usedLanguages) {

    public static MemberSimpleProfile of(final Member member, final CodeNameDto region, final String profileImageUri) {
        return new MemberSimpleProfile(
                member.getUuid(),
                profileImageUri,
                member.getName(),
                member.getDescription(),
                region,
                member.getMemberUsedLanguages().stream().map(UsedLanguageDto::from).toList()
        );
    }
    
}
