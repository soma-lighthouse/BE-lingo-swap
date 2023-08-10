package com.lighthouse.lingoswap.board.repository;

import com.lighthouse.lingoswap.board.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Question, Long> {

    Slice<Question> findQuestionsByCategoryId(Long categoryId, Pageable pageable);
}
