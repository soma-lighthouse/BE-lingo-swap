package com.lighthouse.lingoswap.question.dto;

import java.util.List;

public record QuestionListResponse(Long nextId, List<QuestionDetail> questions) {

    public static QuestionListResponse of(Long nextId, List<QuestionDetail> questions) {
        return new QuestionListResponse(nextId, questions);
    }
}
