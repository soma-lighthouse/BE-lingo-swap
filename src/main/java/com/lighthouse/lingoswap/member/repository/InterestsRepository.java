package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Interests;
import com.lighthouse.lingoswap.question.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestsRepository extends JpaRepository<Interests, Long> {

    Optional<Interests> findByName(String name);

    List<Interests> findByCategory(Category category);
}
