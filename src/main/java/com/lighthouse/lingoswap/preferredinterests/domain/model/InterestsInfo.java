package com.lighthouse.lingoswap.preferredinterests.domain.model;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class InterestsInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interests_id")
    private Interests interests;

    InterestsInfo(final Interests interests) {
        this.interests = interests;
    }

    String getName() {
        return interests.getName();
    }

    String getCategory() {
        return interests.getInterestsCategory();
    }

    Long getCategoryId() {
        return interests.getCategoryId();
    }

}
