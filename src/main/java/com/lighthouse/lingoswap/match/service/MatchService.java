package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
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

    public SliceDto<MatchedMember> findFilteredMembers(Long memberId, Long nextId, int pageSize) {
        return matchedMemberQueryRepository.findAllByFromMemberId(memberId, nextId, pageSize);
    }

    @Transactional
    public void saveMatchedMembersWithPreferences(
            Long memberId,
            List<Long> preferredCountryIds,
            List<Long> preferredLanguages,
            List<Long> preferredInterests) {
        matchedMemberRepository.deletePreviousMatchedMember(memberId);
        matchedMemberRepository.saveMatchedMembersWithPreferences(
                memberId, preferredCountryIds, preferredLanguages, preferredInterests);
    }

    public void delete() {
        matchedMemberRepository.deleteAll();
    }
}