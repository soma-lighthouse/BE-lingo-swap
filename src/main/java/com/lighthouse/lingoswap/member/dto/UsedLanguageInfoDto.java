package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.Builder;

public record UsedLanguageInfoDto(String code, Integer level) {

    @Builder
    public static UsedLanguageInfoDto of(final String code, final Integer level) {
        return new UsedLanguageInfoDto(code, level);
    }

    public static UsedLanguageInfoDto from(final UsedLanguage usedLanguage) {
        return new UsedLanguageInfoDto(usedLanguage.getCode(), usedLanguage.getLevel());
    }

}
