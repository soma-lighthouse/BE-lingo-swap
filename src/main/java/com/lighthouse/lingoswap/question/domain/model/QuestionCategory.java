package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.category.domain.model.Category;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class QuestionCategory {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    QuestionCategory(final Category category) {
        this.category = category;
    }

    Long getId() {
        return category.getId();
    }

}
