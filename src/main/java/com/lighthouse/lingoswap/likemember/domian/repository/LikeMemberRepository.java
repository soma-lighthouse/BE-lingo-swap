package com.lighthouse.lingoswap.likemember.domian.repository;

import com.lighthouse.lingoswap.likemember.domian.model.LikeMember;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {

    List<LikeMember> findAllByMember(final Member member);

    Optional<LikeMember> findByMemberAndQuestion(final Member member, final Question question);

    default LikeMember getByMemberAndQuestion(final Member member, final Question question) {
        return findByMemberAndQuestion(member, question)
                .orElseThrow(LikeMemberNotFoundException::new);
    }

}
