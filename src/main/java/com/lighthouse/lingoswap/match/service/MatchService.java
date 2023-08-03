package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.repository.MatchQueryRepository;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchQueryRepository matchQueryRepository;

    SliceDto<Member> searchMatchedMemberProfiles(final Long fromMemberId, final Long nextMemberId, final int pageSize) {
        return matchQueryRepository.search(fromMemberId, nextMemberId, pageSize);
    }
}
