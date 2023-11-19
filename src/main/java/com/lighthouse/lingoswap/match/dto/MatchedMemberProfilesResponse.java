package com.lighthouse.lingoswap.match.dto;

import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import lombok.Builder;

import java.util.List;

public record MatchedMemberProfilesResponse(Long nextId, List<MemberSimpleProfile> profiles) {

    @Builder
    public static MatchedMemberProfilesResponse of(Long nextId, List<MemberSimpleProfile> profiles) {
        return new MatchedMemberProfilesResponse(nextId, profiles);
    }

}
