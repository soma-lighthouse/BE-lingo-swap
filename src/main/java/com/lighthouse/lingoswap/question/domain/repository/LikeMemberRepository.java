package com.lighthouse.lingoswap.question.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.LikeMember;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeMemberException;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {

    @Query("select l.question.question from LikeMember l where l.member.member = :member")
    List<Question> findAllQuestionsByMember(@Param("member") final Member member);

    @Query("select l from LikeMember l where l.member.member = :member and l.question.question = :question")
    Optional<LikeMember> findByMemberAndQuestion(@Param("member") final Member member, @Param("question") final Question question);

    default LikeMember getByMemberAndQuestion(final Member member, final Question question) {
        return findByMemberAndQuestion(member, question)
                .orElseThrow(LikeMemberNotFoundException::new);
    }

    default void validateAlreadyLiked(final Member member, final Question question) {
        if (findByMemberAndQuestion(member, question).isPresent()) {
            throw new DuplicateLikeMemberException();
        }
    }

}
