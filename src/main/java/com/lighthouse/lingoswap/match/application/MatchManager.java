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
import java.util.Map;

import static java.util.stream.Collectors.*;

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
        List<MemberSimpleProfile> results = createSimpleProfiles(sliceDto.content());
        return new MatchedMemberProfilesResponse(sliceDto.nextId(), results);
    }

    private List<MemberSimpleProfile> createSimpleProfiles(final List<MatchedMember> matchedMembers) {
        List<Member> toMembers = matchedMembers.stream()
                .map(MatchedMember::getToMember)
                .toList();
        Map<Member, List<String>> map = preferredInterestsRepository.findAllByMemberIn(toMembers).stream()
                .collect(groupingBy(PreferredInterests::getMember, mapping(PreferredInterests::getInterestsName, toList())));
        return map.entrySet().stream()
                .map(e -> MemberSimpleProfile.of(e.getKey(), e.getValue()))
                .toList();
    }

}
