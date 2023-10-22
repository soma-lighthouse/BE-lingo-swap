package com.lighthouse.lingoswap.category.domain.repository;

import com.lighthouse.lingoswap.category.domain.model.Category;
import com.lighthouse.lingoswap.category.exception.CategoryNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getByReferenceId(final Long categoryId) {
        return findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    Optional<Category> findByName(final String name);

    default Category getByName(final String name) {
        return findByName(name)
                .orElseThrow(CategoryNotFoundException::new);
    }

}
