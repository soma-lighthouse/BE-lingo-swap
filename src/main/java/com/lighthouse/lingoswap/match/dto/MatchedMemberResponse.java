package com.lighthouse.lingoswap.match.dto;

import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;

import java.util.List;

public record MatchedMemberResponse(Long lastMemberId, List<MemberSimpleProfile> profiles) {

    public static MatchedMemberResponse of(Long lastMemberId, List<MemberSimpleProfile> profiles) {
        return new MatchedMemberResponse(lastMemberId, profiles);
    }
}
