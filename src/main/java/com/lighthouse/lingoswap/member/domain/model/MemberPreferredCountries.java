package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class MemberPreferredCountries {

    @OneToMany(mappedBy = "member")
    private final List<PreferredCountry> preferredCountries = new ArrayList<>();

}
