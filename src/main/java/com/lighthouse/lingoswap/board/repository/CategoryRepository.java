package com.lighthouse.lingoswap.board.repository;

import com.lighthouse.lingoswap.board.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
