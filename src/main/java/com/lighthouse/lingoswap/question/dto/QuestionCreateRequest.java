package com.lighthouse.lingoswap.question.dto;

public record QuestionCreateRequest(Long userId, Long categoryId, String content) {

}
