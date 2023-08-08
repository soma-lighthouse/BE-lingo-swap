package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.board.repository.BoardRepository;
import com.lighthouse.lingoswap.member.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Question findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
    }

    public void save(Question question) {
        boardRepository.save(question);
    }

    public Slice<Question> findQuestionsByCategory(Category category, Pageable pageable) {
        Slice<Question> slice = boardRepository.findQuestionsByCategory(category, pageable);
        return slice;
    }
}

