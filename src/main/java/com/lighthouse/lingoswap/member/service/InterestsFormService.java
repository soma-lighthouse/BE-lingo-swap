package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.dto.InterestsFormResponse;
import com.lighthouse.lingoswap.member.dto.InterestsFormResponseUnit;
import com.lighthouse.lingoswap.member.entity.Interests;
import com.lighthouse.lingoswap.member.repository.InterestsRepository;
import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InterestsFormService {

    private final InterestsRepository interestsRepository;
    private final CategoryRepository categoryRepository;

    public InterestsFormResponse getAllInterests() {
        List<String> categories = Arrays.asList("요리", "게임", "영화", "음악", "여행", "공부");
        List<InterestsFormResponseUnit> interestsList = new ArrayList<>();

        for (String categoryName : categories) {
            Category category = categoryRepository.findByName(categoryName);
            List<Interests> interests = interestsRepository.findByCategory(category);
            List<String> interestsName = new ArrayList<>();
            for (Interests interestsElement : interests) {
                interestsName.add(interestsElement.getName());
            }
            InterestsFormResponseUnit interestsFormResponseUnit = new InterestsFormResponseUnit(categoryName, interestsName);
            interestsList.add(interestsFormResponseUnit);
        }
        return new InterestsFormResponse(interestsList);
    }
}
