package com.lighthouse.lingoswap.preferredinterests.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PreferredInterests extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PreferredInterestsMember member;

    @Embedded
    private InterestsInfo interests;

    @Builder
    public PreferredInterests(Member member, Interests interests) {
        this.member = new PreferredInterestsMember(member);
        this.interests = new InterestsInfo(interests);
    }

    public String getInterestsName() {
        return interests.getName();
    }

    public String getInterestsCategory() {
        return interests.getCategory();
    }

    public Long getInterestsCategoryId() {
        return interests.getCategoryId();
    }

}
