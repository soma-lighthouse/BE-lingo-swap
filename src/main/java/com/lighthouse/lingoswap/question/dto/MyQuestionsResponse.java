package com.lighthouse.lingoswap.question.dto;

import java.util.List;

public record MyQuestionsResponse(List<MyQuestionDetail> questions) {

    public static MyQuestionsResponse from(final List<MyQuestionDetail> questions) {
        return new MyQuestionsResponse(questions);
    }

}
