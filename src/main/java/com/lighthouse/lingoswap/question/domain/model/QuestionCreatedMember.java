package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class QuestionCreatedMember {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_member_id")
    private Member member;

    QuestionCreatedMember(final Member member) {
        this.member = member;
    }

    String getName() {
        return member.getName();
    }

    String getUuid() {
        return member.getUuid();
    }

    String getRegion() {
        return member.getRegion();
    }

    String getProfileImageUrl() {
        return member.getProfileImageUrl();
    }

}
