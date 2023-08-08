package com.lighthouse.lingoswap.match.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.match.repository.MatchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchQueryRepository matchQueryRepository;

    SliceDto<MatchedMember> searchMatchedMembers(final Long fromMemberId, final Long nextId, final int pageSize) {
        return matchQueryRepository.search(fromMemberId, nextId, pageSize);
    }
}
