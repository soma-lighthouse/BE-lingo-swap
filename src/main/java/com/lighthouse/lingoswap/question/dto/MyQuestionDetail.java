package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.question.entity.Question;

public record MyQuestionDetail(Long questionId, String contents, Integer likes) {

    public static MyQuestionDetail of(Question question) {
        return new MyQuestionDetail(question.getId(), question.getContents(), question.getLikes());
    }
}


