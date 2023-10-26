package com.lighthouse.lingoswap.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record QuestionCreateRequest(@NotBlank String uuid, @NotNull Long categoryId, @NotBlank String content) {

    @Builder
    public static QuestionCreateRequest of(final String uuid, final Long categoryId, final String content) {
        return new QuestionCreateRequest(uuid, categoryId, content);
    }

}
