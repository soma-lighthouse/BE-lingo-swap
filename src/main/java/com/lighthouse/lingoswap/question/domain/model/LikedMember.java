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
class LikedMember {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    LikedMember(final Member member) {
        this.member = member;
    }

    Long getMemberId() {
        return member.getId();
    }

}
