package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.question.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
