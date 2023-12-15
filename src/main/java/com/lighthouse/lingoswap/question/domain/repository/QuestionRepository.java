package com.lighthouse.lingoswap.question.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.Question;
import com.lighthouse.lingoswap.question.exception.QuestionNotFoundException;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    default Question getByQuestionId(final Long questionId) {
        return findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Question> findById(final Long id);

    @Query("select q from Question q join fetch q.questionCategory.category where q.questionCreatedMember.member = :member order by q.id desc")
    List<Question> findByCreatedMember(@Param("member") final Member member);

}
