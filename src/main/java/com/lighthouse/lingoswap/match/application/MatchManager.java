package com.lighthouse.lingoswap.match.application;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.domain.model.MatchedMember;
import com.lighthouse.lingoswap.match.domain.repository.MatchedMemberQueryRepository;
import com.lighthouse.lingoswap.match.domain.repository.MatchedMemberRepository;
import com.lighthouse.lingoswap.match.dto.MatchedMemberProfilesResponse;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.MemberSimpleProfile;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
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
    private final PreferredInterestsRepository preferredInterestsRepository;

    @Transactional
    public MatchedMemberProfilesResponse read(final String uuid, final Long nextId, final int pageSize) {
        Member fromMember = memberRepository.getByUuid(uuid);
        if (nextId == null) {
            List<String> preferredCountryCodes = preferredCountryRepository.findAllByMember(fromMember)
                    .stream()
                    .map(PreferredCountry::getCode)
                    .toList();

            List<Long> categoryIds = preferredInterestsRepository.findAllByMember(fromMember)
                    .stream()
                    .map(PreferredInterests::getInterestsCategoryId)
                    .toList();

            matchedMemberRepository.deletePreviousMatchedMember(fromMember.getId());
            matchedMemberRepository.saveMatchedMembersWithPreferences(fromMember.getId(), preferredCountryCodes, categoryIds);
        }

        SliceDto<MatchedMember> sliceDto = matchedMemberQueryRepository.findAllByFromMemberId(fromMember.getId(), nextId, pageSize);
        List<String> preferredInterests = preferredInterestsRepository.findAllByMember(fromMember).stream()
                .map(PreferredInterests::getInterestsName)
                .toList();
        List<MemberSimpleProfile> results = sliceDto.content().stream()
                .map(MatchedMember::getToMember)
                .map(m -> MemberSimpleProfile.of(m, m.getProfileImageUri(), m.getRegion(), preferredInterests))
                .toList();
        return new MatchedMemberProfilesResponse(sliceDto.nextId(), results);
    }

}
