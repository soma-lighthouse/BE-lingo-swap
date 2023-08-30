package com.lighthouse.lingoswap.question.dto;

import java.util.List;

public record QuestionReadResponse(Long nextId, List<QuestionResponse> questions) {

    public static QuestionReadResponse of(Long nextId, List<QuestionResponse> questions) {
        return new QuestionReadResponse(nextId, questions);
    }
}
