package com.lighthouse.lingoswap.category.domain.repository;

import com.lighthouse.lingoswap.category.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
