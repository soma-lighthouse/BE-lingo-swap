package com.lighthouse.lingoswap.language.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;

import java.util.List;

public record LanguageFormResponse(List<CodeNameDto> languageForm) {

    public static LanguageFormResponse from(final List<CodeNameDto> languageForm) {
        return new LanguageFormResponse(languageForm);
    }

}
