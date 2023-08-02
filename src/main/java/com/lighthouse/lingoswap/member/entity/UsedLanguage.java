package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    private Integer level;
    private Boolean isValid;

    public UsedLanguage(final Member member, final Language language, final Integer level) {
        this.member = member;
        this.language = language;
        this.level = level;
    }
}
