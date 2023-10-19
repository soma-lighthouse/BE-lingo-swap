package com.lighthouse.lingoswap.preferredcountry.domain.model;

import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PreferredCountryMember {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    PreferredCountryMember(final Member member) {
        this.member = member;
    }

}
