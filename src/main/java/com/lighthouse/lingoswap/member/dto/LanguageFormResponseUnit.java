package com.lighthouse.lingoswap.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LanguageFormResponseUnit {

    private String code;
    private String name;

    @QueryProjection
    public LanguageFormResponseUnit(final String code, final String name) {
        this.code = code;
        this.name = name;
    }
}
