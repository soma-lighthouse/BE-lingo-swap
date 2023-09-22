package com.lighthouse.lingoswap.question.dto;

import java.util.List;

public record QuestionRecommendationListResponse(Long nextId, List<String> questions) {

    public static QuestionRecommendationListResponse of(Long nextId, List<String> questions) {
        return new QuestionRecommendationListResponse(nextId, questions);
    }
}

