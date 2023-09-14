package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    @Query("select q from Question q join fetch q.category where q.createdMember = :member order by q.id desc")
    List<Question> findByCreatedMember(@Param("member") Member member);
}
