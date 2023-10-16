package com.lighthouse.lingoswap.usedlanguage.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UsedLanguage extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    private Integer level;
    private Boolean isValid;

    public UsedLanguage(final Member member, final Language language, final Integer level) {
        this.member = member;
        this.language = language;
        this.level = level;
    }

}
