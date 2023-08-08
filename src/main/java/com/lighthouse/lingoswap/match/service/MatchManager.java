package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchManager {

    private final MatchService matchService;
    private final MemberService memberService;

    public ResponseDto<MatchedMemberProfilesResponse> read(final Long fromMemberId, final Long nextId, final int pageSize) {
        SliceDto<MatchedMember> matchedMembers = matchService.searchMatchedMembers(fromMemberId, nextId, pageSize);
        List<Long> ids = matchedMembers.content().stream().map(m -> m.getToMember().getId()).toList();
        List<Member> members = memberService.findAllById(ids);
        MatchedMemberProfilesResponse results = toDto(matchedMembers.nextId(), members);
        return ResponseDto.<MatchedMemberProfilesResponse>builder()
                .code("20000")
                .message("Successfully user matched")
                .data(results)
                .build();
    }

    private MatchedMemberProfilesResponse toDto(final Long nextId, final List<Member> members) {
        List<MemberSimpleProfile> profiles = members.stream().map(MemberSimpleProfile::from).toList();
        return MatchedMemberProfilesResponse.of(nextId, profiles);
    }
}
