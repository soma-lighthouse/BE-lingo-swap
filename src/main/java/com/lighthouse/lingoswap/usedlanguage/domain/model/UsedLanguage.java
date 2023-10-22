package com.lighthouse.lingoswap.usedlanguage.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UsedLanguage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UsedLanguageMember member;

    @Embedded
    private LanguageInfo languageInfo;

    private Integer level;
    private Boolean isValid;

    @Builder
    public UsedLanguage(final Member member, final Language language, final Integer level) {
        this.member = new UsedLanguageMember(member);
        this.languageInfo = new LanguageInfo(language);
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return languageInfo.getCode();
    }

    public String getName() {
        return languageInfo.getName();
    }

    public Integer getLevel() {
        return level;
    }

}
