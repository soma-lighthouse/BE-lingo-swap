package com.lighthouse.lingoswap.interests.application;

import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.interests.dto.InterestsFormResponse;
import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class InterestsManager {

    private final InterestsRepository interestsRepository;
    private final MessageService messageService;

    public InterestsFormResponse readForm() {
        List<Interests> interests = interestsRepository.findAll();
        return new InterestsFormResponse(
                interests.stream().collect(groupingBy(
                                Interests::getInterestsCategory,
                                mapping(Interests::getName, toList())
                        ))
                        .entrySet().stream()
                        .map(this::toTranslatedCategoryDto)
                        .toList());
    }

    private CategoryInterestsMapDto toTranslatedCategoryDto(final Map.Entry<String, List<String>> e) {
        return CategoryInterestsMapDto.builder()
                .category(messageService.toTranslatedCategoryCodeNameDto(e.getKey()))
                .interests(e.getValue().stream().map(messageService::toTranslatedInterestsCodeNameDto).toList())
                .build();
    }

}
