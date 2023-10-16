package com.lighthouse.lingoswap.category.application;

import com.lighthouse.lingoswap.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryManager {

    private final CategoryRepository categoryRepository;

}
