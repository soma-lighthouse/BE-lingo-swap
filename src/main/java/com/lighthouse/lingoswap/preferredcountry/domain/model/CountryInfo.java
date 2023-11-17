package com.lighthouse.lingoswap.preferredcountry.domain.model;

import com.lighthouse.lingoswap.country.domain.model.Country;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CountryInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    Country country;

    CountryInfo(final Country country) {
        this.country = country;
    }

    String getCode() {
        return country.getCode();
    }

}
