package com.lighthouse.lingoswap.question.dto;

import com.lighthouse.lingoswap.question.entity.Category;

import java.util.List;

public record MyQuestionDetailList(String category, List<MyQuestionDetail> myQuestions) {

    public static MyQuestionDetailList of(Category category, List<MyQuestionDetail> myQuestions) {
        return new MyQuestionDetailList(category.getName(), myQuestions);
    }
}
