package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.question.entity.Question;
import com.lighthouse.lingoswap.question.repository.QuestionQueryRepository;
import com.lighthouse.lingoswap.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository questionQueryRepository;

    Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("멤버가 없습니다"));
    }

    void save(Question question) {
        questionRepository.save(question);
    }

    SliceDto<Question> search(Long categoryId, Long nextId, int pageSize) {
        return questionQueryRepository.findQuestionsByCategoryId(categoryId, nextId, pageSize);
    }

    public List<Question> searchMyQuestion(Member member) {
        return questionRepository.findByCreatedMember(member);
    }

    SliceDto<Question> searchRecommendation(Long categoryId, Long nextId, int pageSize) {
        return questionQueryRepository.findQuestionRecommendationsByCategoryId(categoryId, nextId, pageSize);
    }
}

