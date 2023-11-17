package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.question.domain.model.Question;
import lombok.Builder;

import java.time.LocalDateTime;

public record MyQuestionDetail(Long questionId,
                               String contents,
                               Long likes,
                               Long category,
                               LocalDateTime createdAt) {

    @Builder
    public static MyQuestionDetail of(final Long questionId,
                                      final String contents,
                                      final Long likes,
                                      final Long category,
                                      final LocalDateTime createdAt) {
        return new MyQuestionDetail(questionId, contents, likes, category, createdAt);
    }

    public static MyQuestionDetail from(Question question) {
        return new MyQuestionDetail(question.getId(), question.getContents(), question.getLike(), question.getCategoryId(), question.getCreatedAt());
    }

}


