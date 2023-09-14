package com.lighthouse.lingoswap.question.service;

import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    Category findById(final Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("카테고리가 없습니다"));
    }

    public Category findByName(final String name) {
        return categoryRepository.findByName(name);
    }
}
