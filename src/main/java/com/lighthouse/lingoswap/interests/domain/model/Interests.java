package com.lighthouse.lingoswap.interests.domain.model;

import com.lighthouse.lingoswap.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Interests extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private InterestsCategory interestsCategory;

    private String name;

    public String getInterestsCategory() {
        return interestsCategory.getName();
    }

    public Long getCategoryId() {
        return interestsCategory.getId();
    }

    public String getName() {
        return name;
    }

}
