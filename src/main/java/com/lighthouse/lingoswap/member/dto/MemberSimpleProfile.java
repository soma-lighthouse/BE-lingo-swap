package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;

import java.util.List;

public record MemberSimpleProfile(Long id,
                                  String profileImage,
                                  String name,
                                  String description,
                                  String region,
                                  List<MemberLanguageResponse> languages) {

    public static MemberSimpleProfile of(Member member, List<UsedLanguage> usedLanguages) {
        return new MemberSimpleProfile(
                member.getId(),
                member.getProfileImage(),
                member.getName(),
                member.getDescription(),
                member.getRegion(),
                usedLanguages.stream().map(MemberLanguageResponse::from).toList()
        );
    }
}
