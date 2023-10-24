package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

public record UsedLanguageInfoDto(@NotBlank String code,
                                  @Range(min = 1, max = 5) Integer level) {

    @Builder
    public static UsedLanguageInfoDto of(final String code, final Integer level) {
        return new UsedLanguageInfoDto(code, level);
    }

    public static UsedLanguageInfoDto from(final UsedLanguage usedLanguage) {
        return new UsedLanguageInfoDto(usedLanguage.getCode(), usedLanguage.getLevel());
    }

}
