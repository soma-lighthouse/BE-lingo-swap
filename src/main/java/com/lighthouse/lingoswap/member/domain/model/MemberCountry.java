package com.lighthouse.lingoswap.member.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class MemberCountry {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Country country;

    MemberCountry(final Country country) {
        this.country = country;
    }

    String getCode() {
        return country.getCode();
    }

}
