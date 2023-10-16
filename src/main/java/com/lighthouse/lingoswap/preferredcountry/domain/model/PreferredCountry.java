package com.lighthouse.lingoswap.preferredcountry.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.member.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PreferredCountry extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private Boolean isValid;

    public PreferredCountry(Member member, Country country) {
        this.member = member;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getCountryCode() {
        return country.getCode();
    }

}
