package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.repository.LikeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeMemberService {

    private final LikeMemberRepository likeMemberRepository;

    public void save(LikeMember likeMember) {
        likeMemberRepository.save(likeMember);
    }
}
