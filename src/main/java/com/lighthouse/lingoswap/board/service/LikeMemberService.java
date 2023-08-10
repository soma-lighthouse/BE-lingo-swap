package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.repository.LikeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeMemberService {

    private final LikeMemberRepository likeMemberRepository;

    void save(LikeMember likeMember) {
        likeMemberRepository.save(likeMember);
    }

    List<LikeMember> findAllByMemberId(Long memberId) {
        return likeMemberRepository.findAllByMemberId(memberId);
    }
}
