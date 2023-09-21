package com.lighthouse.lingoswap.question.dto;

import jakarta.validation.constraints.NotNull;

public record QuestionCreateRequest(String uuid, Long categoryId, @NotNull(message = "질문을 입력해주세요") String content) {

}
