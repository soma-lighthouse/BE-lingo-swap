package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Member;

import java.util.List;

public record MemberSimpleProfile(Long id,
                                  String profileImageUri,
                                  String name,
                                  String description,
                                  String region,
                                  List<MemberUsedLanguage> languages) {

    public static MemberSimpleProfile from(Member member) {
        return new MemberSimpleProfile(
                member.getId(),
                member.getProfileImageUri(),
                member.getName(),
                member.getDescription(),
                member.getRegion().getCode(),
                member.getUsedLanguages().stream().map(MemberUsedLanguage::from).toList()
        );
    }
}
