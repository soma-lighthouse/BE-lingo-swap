package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.match.repository.MatchedMemberQueryRepository;
import com.lighthouse.lingoswap.match.repository.MatchedMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchedMemberQueryRepository matchedMemberQueryRepository;
    private final MatchedMemberRepository matchedMemberRepository;

/*    SliceDto<MatchedMember> search(final Long fromMemberId, final Long nextId, final int pageSize) {
        return matchedMemberQueryRepository.findAllByFromMemberId(fromMemberId, nextId, pageSize);
    }*/

    public Slice<Long> findMatchedMembersWithPreferences(
            Long memberId,
            int region,
            List<Long> preferredLanguages,
            List<Long> preferredInterests,
            Pageable pageable) {
        return matchedMemberRepository.findMatchedMembersWithPreferences(
                memberId, region, preferredLanguages, preferredInterests, pageable);
    }
}