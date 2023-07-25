package com.lighthouse.lingoswap.question.entity;

import com.lighthouse.lingoswap.common.entity.DateBasicEntity;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question extends DateBasicEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createdMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private Integer likes;
    private String contents;
    private Boolean isValid;
    private Boolean isRecommended;
}