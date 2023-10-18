package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.application.PreferredCountryService;
import com.lighthouse.lingoswap.member.application.UsedLanguageService;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.CodeNameDto;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredinterests.application.PreferredInterestsManager;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchManager {

    private final MatchService matchService;
    private final MemberRepository memberRepository;
    private final PreferredCountryService preferredCountryService;
    private final UsedLanguageService usedLanguageService;
    private final PreferredInterestsManager preferredInterestsManager;
    private final CloudFrontService cloudFrontService;
    private final MessageSourceManager messageSourceManager;

    @Transactional
    public ResponseDto<MatchedMemberProfilesResponse> read(final String uuid, final Long nextId, final int pageSize) {
        Member fromMember = memberRepository.getByUuid(uuid);
        if (nextId == null) {
            List<Long> usedLanguageIds = usedLanguageService.findByMember(fromMember)
                    .stream()
                    .map(UsedLanguage::getId)
                    .toList();

            List<Long> preferredCountryIds = preferredCountryService.findByMember(fromMember)
                    .stream()
                    .map(PreferredCountry::getId)
                    .toList();

            List<Long> categoryIds = preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(fromMember.getId())
                    .stream()
                    .map(PreferredInterests::getInterestsCategoryId)
                    .toList();

            matchService.saveMatchedMembersWithPreferences(
                    fromMember.getId(), preferredCountryIds, usedLanguageIds, categoryIds);
        }

        SliceDto<MatchedMember> matchedMembers = matchService.findFilteredMembers(fromMember.getId(), nextId, pageSize);
        List<MemberSimpleProfile> results = matchedMembers.content().stream().map(MatchedMember::getToMember)
                .map(m -> MemberSimpleProfile.of(
                        m,
                        new CodeNameDto(m.getRegion(), messageSourceManager.translate(m.getRegion())),
                        cloudFrontService.addEndpoint(m.getProfileImageUrl())))
                .toList();
        return ResponseDto.success(new MatchedMemberProfilesResponse(matchedMembers.nextId(), results));
    }

}
