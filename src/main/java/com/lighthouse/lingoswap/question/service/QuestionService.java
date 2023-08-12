package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.question.entity.Question;
import com.lighthouse.lingoswap.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
    }

    void save(Question question) {
//        Question question = Question.of()
        questionRepository.save(question);
    }

    Slice<Question> findQuestionsByCategory(Long categoryId, Pageable pageable) {
        return questionRepository.findQuestionsByCategoryId(categoryId, pageable);
    }
}

