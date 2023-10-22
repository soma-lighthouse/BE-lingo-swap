package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.Builder;

@Builder
public record UsedLanguageDto(String code, String name, int level) {

    public static UsedLanguageDto from(UsedLanguage usedLanguage) {
        return new UsedLanguageDto(usedLanguage.getCode(), usedLanguage.getName(), usedLanguage.getLevel());
    }

}
