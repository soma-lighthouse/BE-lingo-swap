package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.question.domain.model.Question;

import java.time.LocalDateTime;

public record MyQuestionDetail(Long questionId,
                               String contents,
                               Long likes,
                               Long category,
                               LocalDateTime createdAt) {

    public static MyQuestionDetail from(Question question) {
        return new MyQuestionDetail(question.getId(), question.getContents(), question.getLike(), question.getCategoryId(), question.getCreatedAt());
    }

}


