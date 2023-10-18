package com.lighthouse.lingoswap.question.domain.model;

import com.lighthouse.lingoswap.category.domain.model.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class QuestionCategory {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    QuestionCategory(final Category category) {
        this.category = category;
    }

    Long getId() {
        return category.getId();
    }

}
