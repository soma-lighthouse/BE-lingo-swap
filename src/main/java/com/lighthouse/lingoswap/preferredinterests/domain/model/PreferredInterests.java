package com.lighthouse.lingoswap.preferredinterests.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PreferredInterests extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interests_id")
    private Interests interests;

    private Boolean isValid;

    public PreferredInterests(Member member, Interests interests) {
        this.member = member;
        this.interests = interests;
    }

    public String getInterestsName() {
        return interests.getName();
    }

    public String getInterestsCategory() {
        return interests.getInterestsCategory();
    }

    public Long getInterestsCategoryId() {
        return interests.getCategoryId();
    }

}
