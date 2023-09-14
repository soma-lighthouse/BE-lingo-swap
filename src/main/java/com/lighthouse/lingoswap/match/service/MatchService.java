package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.match.repository.MatchedMemberQueryRepository;
import com.lighthouse.lingoswap.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchedMemberQueryRepository matchedMemberQueryRepository;

    SliceDto<MatchedMember> search(final Member member, final Long nextId, final int pageSize) {
        return matchedMemberQueryRepository.findAllByMember(member, nextId, pageSize);
    }
}
