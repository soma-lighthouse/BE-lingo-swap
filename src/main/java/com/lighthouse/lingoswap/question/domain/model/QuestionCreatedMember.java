package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class QuestionCreatedMember {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_member_id")
    private Member member;

    public QuestionCreatedMember(final Member member) {
        this.member = member;
    }

    public String getName() {
        return member.getName();
    }

    String getUuid() {
        return member.getUuid();
    }

    String getRegion() {
        return member.getRegionCode();
    }

    String getProfileImageUri() {
        return member.getProfileImageUri();
    }

}
