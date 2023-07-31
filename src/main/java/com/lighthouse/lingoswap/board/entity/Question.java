package com.lighthouse.lingoswap.board.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createdMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private Integer likes;
    private String contents;
    private Boolean isValid;
    private Boolean isRecommended;


    @Builder
    public Question(Member createdMember, Category category, Integer likes, String contents) {
        this.createdMember = createdMember;
        this.category = category;
        this.likes = likes;
        this.contents = contents;
        this.isValid = Boolean.FALSE;
        this.isRecommended = Boolean.FALSE;
    }
}
