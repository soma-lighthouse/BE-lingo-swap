package com.lighthouse.lingoswap.interests.domain.model;

import com.lighthouse.lingoswap.category.domain.model.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class InterestsCategory {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    String getName() {
        return category.getName();
    }

    Long getId() {
        return category.getId();
    }

}
