package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.LikeMember;
import com.lighthouse.lingoswap.question.entity.Question;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeException;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import com.lighthouse.lingoswap.question.repository.LikeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeMemberService {

    private final LikeMemberRepository likeMemberRepository;

    void save(final LikeMember likeMember) {
        try {
            likeMemberRepository.save(likeMember);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateLikeException();
        }
    }

    List<LikeMember> findAllByMember(final Member member) {
        return likeMemberRepository.findAllByMember(member);
    }

    public LikeMember findByMemberAndQuestion(final Member member, final Question question) {
        return likeMemberRepository.findByMemberAndQuestion(member, question)
                .orElseThrow(() -> new LikeMemberNotFoundException(member.getAuthDetails().getUsername(), question.getId()));
    }

    void delete(final LikeMember likeMember) {
        likeMemberRepository.delete(likeMember);
    }
}
