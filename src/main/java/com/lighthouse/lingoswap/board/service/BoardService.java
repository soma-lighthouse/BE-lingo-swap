package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.LikeMember;
import com.lighthouse.lingoswap.board.entity.Question;
import com.lighthouse.lingoswap.board.repository.BoardRepository;
import com.lighthouse.lingoswap.board.repository.LikeMemberRepository;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Question findById(Long id) {
        Question question = boardRepository.findById(id).get();
        return question;
    }

    public void save(Question question) {
        boardRepository.save(question);
    } //맥스아이디가 필요할 때가 있음

    public Slice<Question> findQuestionsByCategory(Category category, Pageable pageable) {
        Slice<Question> slice = boardRepository.findQuestionsByCategory(category, pageable);
        return slice;
    }
}

