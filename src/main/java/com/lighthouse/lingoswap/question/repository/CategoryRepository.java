package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.question.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("select distinct c from Category c join fetch c.interests")
    List<Category> findAllWithCategory();
}
