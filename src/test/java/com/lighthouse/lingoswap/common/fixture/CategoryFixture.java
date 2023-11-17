package com.lighthouse.lingoswap.common.fixture;

import com.lighthouse.lingoswap.category.domain.model.Category;

public class CategoryFixture {

    public static final String CATEGORY_NAME = CategoryType.FOOD.getName();

    public static Category category() {
        return Category.builder()
                .name(CATEGORY_NAME)
                .build();
    }

}
