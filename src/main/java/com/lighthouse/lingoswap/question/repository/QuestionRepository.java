package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
