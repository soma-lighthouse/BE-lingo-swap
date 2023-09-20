package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.UsedLanguage;

import java.util.Objects;

public record UsedLanguageInfo(String code, Integer level) {

    public static UsedLanguageInfo from(UsedLanguage usedLanguage) {
        return new UsedLanguageInfo(usedLanguage.getLanguage().getCode(), usedLanguage.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsedLanguageInfo that = (UsedLanguageInfo) o;
        return Objects.equals(level, that.level()) &&
                Objects.equals(code, that.code());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, level);
    }
}
