package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("카테고리가 없습니다"));
    }
}