package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class MemberPreferredInterests {

    @OneToMany(mappedBy = "member")
    private final List<PreferredInterests> preferredInterests = new ArrayList<>();

}
