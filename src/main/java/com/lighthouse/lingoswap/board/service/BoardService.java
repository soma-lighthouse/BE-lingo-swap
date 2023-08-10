package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    Question findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
    }

    void save(Question question) {
//        Question question = Question.of()
        boardRepository.save(question);
    }

    Slice<Question> findQuestionsByCategory(Long categoryId, Pageable pageable) {
        return boardRepository.findQuestionsByCategoryId(categoryId, pageable);
    }
}

