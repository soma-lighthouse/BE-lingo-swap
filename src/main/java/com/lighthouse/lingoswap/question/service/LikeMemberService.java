package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.LikeMember;
import com.lighthouse.lingoswap.question.entity.Question;
import com.lighthouse.lingoswap.question.repository.LikeMemberRepository;
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

    LikeMember findByMemberId(Long memberId) {
        return likeMemberRepository.findByMemberId(memberId);
    }

    List<LikeMember> findAllByMemberId(Long memberId) {
        return likeMemberRepository.findAllByMemberId(memberId);
    }

    void deleteByQuestionAndMember(Question question, Member member) {
        likeMemberRepository.deleteByQuestionAndMember(question, member);
    }
}
