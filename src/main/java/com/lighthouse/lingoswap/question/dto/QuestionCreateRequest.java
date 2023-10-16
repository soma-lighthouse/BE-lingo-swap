package com.lighthouse.lingoswap.question.dto;

import jakarta.validation.constraints.NotNull;

public record QuestionCreateRequest(@NotNull String uuid, @NotNull Long categoryId, @NotNull String content) {

}
