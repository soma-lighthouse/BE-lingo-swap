package com.lighthouse.lingoswap.likemember.domian.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.question.domain.model.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class LikeMember extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private Boolean isValid;

    private LikeMember(final Member member, final Question question) {
        this.member = member;
        this.question = question;
        this.isValid = Boolean.FALSE;
    }

    public static LikeMember of(final Member member, final Question question) {
        return new LikeMember(member, question);
    }

}
