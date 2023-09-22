package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Member;

import java.util.List;

public record MemberSimpleProfile(String uuid,
                                  String profileImageUri,
                                  String name,
                                  String description,
                                  String region,
                                  List<MemberUsedLanguage> languages) {

    public static MemberSimpleProfile of(final Member member, final String profileImageUri) {
        return new MemberSimpleProfile(
                member.getAuthDetails().getUuid(),
                profileImageUri,
                member.getName(),
                member.getDescription(),
                member.getRegion().getCode(),
                member.getUsedLanguages().stream().map(MemberUsedLanguage::from).toList()
        );
    }
}
