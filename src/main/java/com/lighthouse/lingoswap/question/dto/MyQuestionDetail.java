package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.question.entity.Question;

import java.time.LocalDateTime;

public record MyQuestionDetail(Long questionId, String contents, Integer likes, Long category, LocalDateTime createAt) {

    public static MyQuestionDetail from(Question question) {
        return new MyQuestionDetail(question.getId(), question.getContents(), question.getLikes(), question.getCategory().getId(), question.getCreatedAt());
    }
}


