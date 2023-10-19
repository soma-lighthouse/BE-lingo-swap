package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;

public record UsedLanguageInfoDto(String code, Integer level) {

    public static UsedLanguageInfoDto from(final UsedLanguage usedLanguage) {
        return new UsedLanguageInfoDto(usedLanguage.getCode(), usedLanguage.getLevel());
    }

}
