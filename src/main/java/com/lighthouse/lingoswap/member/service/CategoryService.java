package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.member.repository.CategoryRepository;
import com.lighthouse.lingoswap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category findById(Long id) {
        Category category = categoryRepository.findById(id).get();
        return category;
    }
}