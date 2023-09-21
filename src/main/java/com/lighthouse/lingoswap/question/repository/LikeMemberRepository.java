package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.LikeMember;
import com.lighthouse.lingoswap.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {

    LikeMember findByMemberId(Long memberId);

    List<LikeMember> findAllByMember(Member member);

    void deleteByQuestionAndMember(Question question, Member member);
}
