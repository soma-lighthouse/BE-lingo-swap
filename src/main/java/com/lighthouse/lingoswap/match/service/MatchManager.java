package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.member.entity.*;
import com.lighthouse.lingoswap.member.service.MemberService;
import com.lighthouse.lingoswap.member.service.PreferredCountryService;
import com.lighthouse.lingoswap.member.service.PreferredInterestsService;
import com.lighthouse.lingoswap.member.service.UsedLanguageService;
import com.lighthouse.lingoswap.question.entity.Category;
import lombok.RequiredArgsConstructor;
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

    public ResponseDto<MatchedMemberProfilesResponse> read(final String uuid, final Long nextId, final int pageSize) {
        Member fromMember = memberService.findByUuid(uuid);
        if (nextId == null) {
            List<Long> usedLanguageIds = usedLanguageService.findByMember(fromMember)
                    .stream()
                    .map(UsedLanguage::getId)
                    .toList();

            List<Long> preferredCountryIds = preferredCountryService.findByMember(fromMember)
                    .stream()
                    .map(PreferredCountry::getId)
                    .toList();

            List<Long> categoryIds = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(fromMember.getId())
                    .stream()
                    .map(PreferredInterests::getInterests)
                    .map(Interests::getCategory)
                    .map(Category::getId)
                    .toList();

            matchService.saveMatchedMembersWithPreferences(
                    fromMember.getId(), preferredCountryIds, usedLanguageIds, categoryIds);
        }

        SliceDto<MatchedMember> matchedMembers = matchService.findFilteredMembers(fromMember.getId(), nextId, pageSize);
        List<MemberSimpleProfile> results = matchedMembers.content().stream().map(MatchedMember::getToMember).map(MemberSimpleProfile::from).toList();
        return ResponseDto.success(new MatchedMemberProfilesResponse(matchedMembers.nextId(), results));
    }
}

