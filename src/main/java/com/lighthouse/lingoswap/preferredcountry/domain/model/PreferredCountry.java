package com.lighthouse.lingoswap.preferredcountry.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PreferredCountry extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PreferredCountryMember member;

    @Embedded
    private CountryInfo country;

    @Builder
    public PreferredCountry(final Member member, final Country country) {
        this.member = new PreferredCountryMember(member);
        this.country = new CountryInfo(country);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return country.getCode();
    }

}
