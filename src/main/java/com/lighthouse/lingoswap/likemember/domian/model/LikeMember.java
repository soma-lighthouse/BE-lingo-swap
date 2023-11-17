package com.lighthouse.lingoswap.likemember.domian.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LikeMember extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LikedMember member;

    @Embedded
    private LikedQuestion question;

    @Builder
    public LikeMember(final Member member, final Question question) {
        this.member = new LikedMember(member);
        this.question = new LikedQuestion(question);
    }

    public Long getMemberId() {
        return member.getMemberId();
    }

    public Long getQuestionId() {
        return question.getQuestionId();
    }

}
