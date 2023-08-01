package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.UsedLanguage;

public record MemberLanguageResponse(String code, int level) {

    public static MemberLanguageResponse from(UsedLanguage usedLanguage) {
        return new MemberLanguageResponse(usedLanguage.getLanguage().getCode(), usedLanguage.getLevel());
    }
}
