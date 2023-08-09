package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.match.repository.MatchedMemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchedMemberQueryRepository matchedMemberQueryRepository;

    SliceDto<MatchedMember> search(final Long fromMemberId, final Long nextId, final int pageSize) {
        return matchedMemberQueryRepository.findAllByFromMemberId(fromMemberId, nextId, pageSize);
    }
}
