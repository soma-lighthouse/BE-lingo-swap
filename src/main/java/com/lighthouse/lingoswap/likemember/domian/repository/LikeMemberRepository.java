package com.lighthouse.lingoswap.likemember.domian.repository;

import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {

    LikeMember findByMemberId(Long memberId);

    List<LikeMember> findAllByMember(Member member);

    void deleteByQuestionAndMember(Question question, Member member);

    Optional<LikeMember> findByMemberAndQuestion(Member member, Question question);

}
