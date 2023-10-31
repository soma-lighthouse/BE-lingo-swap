package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.match.repository.MatchedMemberQueryRepository;
import com.lighthouse.lingoswap.match.repository.MatchedMemberRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import com.lighthouse.lingoswap.usedlanguage.domain.repository.UsedLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchManager {

    private final MatchedMemberRepository matchedMemberRepository;
    private final MatchedMemberQueryRepository matchedMemberQueryRepository;
    private final MemberRepository memberRepository;
    private final PreferredCountryRepository preferredCountryRepository;
    private final UsedLanguageRepository usedLanguageRepository;
    private final PreferredInterestsRepository preferredInterestsRepository;
    private final CloudFrontService cloudFrontService;
    private final MessageService messageService;

    @Transactional
    public MatchedMemberProfilesResponse read(final String uuid, final Long nextId, final int pageSize) {
        Member fromMember = memberRepository.getByUuid(uuid);
        if (nextId == null) {
            List<Long> usedLanguageIds = usedLanguageRepository.findAllByMember(fromMember)
                    .stream()
                    .map(UsedLanguage::getId)
                    .toList();

            List<String> preferredCountryCodes = preferredCountryRepository.findAllByMember(fromMember)
                    .stream()
                    .map(PreferredCountry::getCode)
                    .toList();

            List<Long> categoryIds = preferredInterestsRepository.findAllByMember(fromMember)
                    .stream()
                    .map(PreferredInterests::getInterestsCategoryId)
                    .toList();

            matchedMemberRepository.deletePreviousMatchedMember(fromMember.getId());
            matchedMemberRepository.saveMatchedMembersWithPreferences(fromMember.getId(), preferredCountryCodes, usedLanguageIds, categoryIds);
        }

        SliceDto<MatchedMember> sliceDto = matchedMemberQueryRepository.findAllByFromMemberId(fromMember.getId(), nextId, pageSize);
        List<MemberSimpleProfile> results = sliceDto.content().stream()
                .map(MatchedMember::getToMember)
                .map(m -> MemberSimpleProfile.of(
                        m,
                        cloudFrontService.addEndpoint(m.getProfileImageUri()),
                        messageService.toTranslatedCountryCodeNameDto(m.getRegion()),
                        usedLanguageRepository.findAllByMember(m)
                ))
                .toList();
        return new MatchedMemberProfilesResponse(sliceDto.nextId(), results);
    }

}
