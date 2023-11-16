package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.domain.model.Member;

import java.util.List;

public record MemberSimpleProfile(String uuid,
                                  String profileImageUri,
                                  String name,
                                  String description,
                                  String region,
                                  List<String> preferredInterests) {

    public static MemberSimpleProfile of(final Member member,
                                         final String profileImageUri,
                                         final String region,
                                         final List<String> preferredInterests) {
        return new MemberSimpleProfile(
                member.getUuid(),
                profileImageUri,
                member.getName(),
                member.getDescription(),
                region,
                preferredInterests
        );
    }

}
