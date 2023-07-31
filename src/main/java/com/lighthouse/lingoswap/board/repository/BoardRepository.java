package com.lighthouse.lingoswap.board.repository;

import com.lighthouse.lingoswap.board.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Question, Long> {
}
