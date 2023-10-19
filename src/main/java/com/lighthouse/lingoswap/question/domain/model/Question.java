package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.category.domain.model.Category;
import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private QuestionCreatedMember questionCreatedMember;

    @Embedded
    private QuestionCategory questionCategory;

    @Embedded
    private Like like;

    private String contents;
    private Boolean isValid;
    private Boolean isRecommended;

    @Builder
    public Question(final Member member, final Category category, final String contents) {
        this.questionCreatedMember = new QuestionCreatedMember(member);
        this.questionCategory = new QuestionCategory(category);
        this.contents = contents;
        this.like = new Like();
        this.isValid = true;
        this.isRecommended = false;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedMemberUuid() {
        return questionCreatedMember.getUuid();
    }

    public String getCreatedMemberName() {
        return questionCreatedMember.getName();
    }

    public Long getCategoryId() {
        return questionCategory.getId();
    }

    public String getCreatedMemberRegion() {
        return questionCreatedMember.getRegion();
    }

    public String getCreatedMemberProfileImageUrl() {
        return questionCreatedMember.getProfileImageUrl();
    }

    public Long getLike() {
        return like.getValue();
    }

    public String getContents() {
        return contents;
    }

    public void addOneLike() {
        like = like.addOneLike();
    }

    public void subtractOneLike() {
        like = like.subtractOneLike();
    }

}
