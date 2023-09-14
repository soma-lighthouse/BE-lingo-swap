package com.lighthouse.lingoswap.question.dto;

public record QuestionCreateRequest(String userId, Long categoryId, String content) {

}
