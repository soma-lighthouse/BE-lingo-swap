package com.lighthouse.lingoswap.board.entity;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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

    public Question(Member createdMember, Category category, Integer likes, String contents, Boolean isValid, Boolean isRecommended) {
        this.createdMember = createdMember;
        this.category = category;
        this.likes = likes;
        this.contents = contents;
        this.isValid = isValid;
        this.isRecommended = isRecommended;
    }

    public static Question of(Member createdMember, Category category, String contents) {
        return new Question(createdMember, category, 0, contents, Boolean.TRUE, Boolean.FALSE);
    }

    public void addLikes(Question question) {
        this.likes = question.getLikes() + 1;
    }
}
