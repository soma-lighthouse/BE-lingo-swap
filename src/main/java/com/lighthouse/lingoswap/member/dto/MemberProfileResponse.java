package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public record MemberProfileResponse(String profileImage,
                                    String name,
                                    String description,
                                    String region,
                                    List<MemberLanguageResponse> memberLanguageResponses) {

    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getProfileImage(),
                member.getName(),
                member.getDescription(),
                member.getRegion(),
                member.getUsedLanguages().stream().map(MemberLanguageResponse::from).collect(Collectors.toList())
        );
    }
}
