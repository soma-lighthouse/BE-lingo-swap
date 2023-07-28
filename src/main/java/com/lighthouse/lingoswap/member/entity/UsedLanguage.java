package com.lighthouse.lingoswap.member.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UsedLanguage extends BaseEntity {

        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        private Member member;

        @ManyToOne(fetch = FetchType.LAZY)
        private Language language;

        private Integer level;
        private Boolean isValid;
}
