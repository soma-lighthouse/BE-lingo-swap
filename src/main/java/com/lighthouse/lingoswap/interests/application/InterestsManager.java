package com.lighthouse.lingoswap.interests.application;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.interests.dto.InterestsFormResponse;
import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class InterestsManager {

    private final InterestsRepository interestsRepository;
    private final MessageService messageService;

    public ResponseDto<InterestsFormResponse> readInterestsForm() {
        List<Interests> interests = interestsRepository.findAll();
        InterestsFormResponse interestsFormResponse = new InterestsFormResponse(
                interests.stream().collect(groupingBy(
                                Interests::getInterestsCategory,
                                mapping(Interests::getName, toList())
                        ))
                        .entrySet().stream()
                        .map(e -> CategoryInterestsMapDto.builder()
                                .category(messageService.toTranslatedCodeNameDto(e.getKey()))
                                .interests(e.getValue().stream().map(messageService::toTranslatedCodeNameDto).toList())
                                .build())
                        .toList());
        return ResponseDto.success(interestsFormResponse);
    }

}
