package com.lighthouse.lingoswap.board.service;

import com.lighthouse.lingoswap.board.entity.Category;
import com.lighthouse.lingoswap.board.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("카테고리가 없습니다"));
    }
}
