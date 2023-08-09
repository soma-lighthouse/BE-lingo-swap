package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.UsedLanguage;

public record MemberUsedLanguage(String code, int level) {

    public static MemberUsedLanguage from(UsedLanguage usedLanguage) {
        return new MemberUsedLanguage(usedLanguage.getLanguage().getCode(), usedLanguage.getLevel());
    }
}
