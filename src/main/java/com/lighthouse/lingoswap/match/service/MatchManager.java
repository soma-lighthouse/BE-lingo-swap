package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.member.entity.Interests;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import com.lighthouse.lingoswap.member.service.MemberService;
import com.lighthouse.lingoswap.member.service.PreferredCountryService;
import com.lighthouse.lingoswap.member.service.PreferredInterestsService;
import com.lighthouse.lingoswap.member.service.UsedLanguageService;
import com.lighthouse.lingoswap.question.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchManager {

    private final MatchService matchService;
    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final UsedLanguageService usedLanguageService;
    private final PreferredInterestsService preferredInterestsService;

/*    public MatchedMemberProfilesResponse read(final Long fromMemberId, final Long nextId, final int pageSize) {
        SliceDto<MatchedMember> matchedMembers = matchService.search(fromMemberId, nextId, pageSize);
        List<Long> toMemberIds = matchedMembers.content().stream().map(m -> m.getToMember().getId()).toList();
        List<Member> members = memberService.findAllByIdsWithRegionAndUsedLanguage(toMemberIds);
        return toDto(matchedMembers.nextId(), members);
    }*/

/*    private MatchedMemberProfilesResponse toDto(final Long nextId, final List<Member> members) {
        List<MemberSimpleProfile> profiles = members.stream().map(MemberSimpleProfile::from).toList();
        return MatchedMemberProfilesResponse.of(nextId, profiles);
    }*/

    public Slice<Member> read(Long memberId, Pageable pageable) {
        Member fromMember = memberService.findById(memberId);
        int region = fromMember.getRegion().getId();
        List<Long> usedLanguageIds = usedLanguageService.findByMember(fromMember)
                .stream()
                .map(UsedLanguage::getId)
                .toList();
        List<Long> categoryIds = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(memberId)
                .stream()
                .map(PreferredInterests::getInterests)
                .map(Interests::getCategory)
                .map(Category::getId)
                .toList();
        Slice<Long> matchedMemberIds = matchService.findMatchedMembersWithPreferences(memberId, region, usedLanguageIds, categoryIds, pageable);
        return matchedMemberIds.map(memberService::findById);
    }
}
