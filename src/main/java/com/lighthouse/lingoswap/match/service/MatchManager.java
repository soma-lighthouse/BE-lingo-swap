package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.dto.MatchedMemberResponse;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import com.lighthouse.lingoswap.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MatchManager {

    private final MatchService matchService;
    private final MemberService memberService;

    private static MatchedMemberResponse toDto(final SliceDto<Member> matchedMembers, final Map<Long, List<UsedLanguage>> usedLanguagesMap) {
        List<MemberSimpleProfile> profiles = matchedMembers.content().stream().map(m -> MemberSimpleProfile.of(m, usedLanguagesMap.get(m.getId()))).toList();
        return MatchedMemberResponse.of(matchedMembers.nextId(), profiles);
    }

    public ResponseDto<MatchedMemberResponse> read(final Long fromMemberId, final Long nextMemberId, final int pageSize) {
        SliceDto<Member> matchedMembers = matchService.searchMatchedMemberProfiles(fromMemberId, nextMemberId, pageSize);
        Map<Long, List<UsedLanguage>> usedLanguagesMap = memberService.findUsedLanguagesByMembers(matchedMembers.content());
        MatchedMemberResponse results = toDto(matchedMembers, usedLanguagesMap);
        return ResponseDto.<MatchedMemberResponse>builder()
                .code(20000)
                .message("Successfully user matched")
                .data(results)
                .build();
    }
}
