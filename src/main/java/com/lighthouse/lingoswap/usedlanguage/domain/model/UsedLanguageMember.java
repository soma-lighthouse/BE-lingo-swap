package com.lighthouse.lingoswap.usedlanguage.domain.model;

import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class UsedLanguageMember {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    UsedLanguageMember(final Member member) {
        this.member = member;
    }

}
