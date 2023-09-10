package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.match.repository.MatchedMemberQueryRepository;
import com.lighthouse.lingoswap.match.repository.MatchedMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchedMemberQueryRepository matchedMemberQueryRepository;
    private final MatchedMemberRepository matchedMemberRepository;

    @Transactional
    public void saveFilteredMembersWithPreferences(
            Long memberId,
            List<Long> preferredCountryIds,
            List<Long> preferredLanguages,
            List<Long> preferredInterests) {
        matchedMemberRepository.deletePreviousFilteredMember(memberId);
        matchedMemberRepository.saveFilteredMembersWithPreferences(
                memberId, preferredCountryIds, preferredLanguages, preferredInterests);
    }
}