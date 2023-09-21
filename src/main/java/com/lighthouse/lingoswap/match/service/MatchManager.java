package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.match.entity.FilteredMember;
import com.lighthouse.lingoswap.member.entity.*;
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
    private final FilterService filterService;
    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final UsedLanguageService usedLanguageService;
    private final PreferredInterestsService preferredInterestsService;

    public Slice<Member> read(final String uuid, Pageable pageable) {
        Member fromMember = memberService.findByUuid(uuid);
        if (pageable.getPageNumber() == 0) {
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

            matchService.saveFilteredMembersWithPreferences(
                    fromMember.getId(), preferredCountryIds, usedLanguageIds, categoryIds);
        }

        Slice<FilteredMember> filteredMembers = filterService.findFilteredMembers(fromMember, pageable);
        return filteredMembers.map(FilteredMember::getToMember);
    }
}

