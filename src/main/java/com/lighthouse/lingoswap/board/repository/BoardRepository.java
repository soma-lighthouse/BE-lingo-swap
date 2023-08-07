package com.lighthouse.lingoswap.board.repository;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.member.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Question, Long> {

    Slice<Question> findQuestionsByCategory(Category category, Pageable pageable);
}
