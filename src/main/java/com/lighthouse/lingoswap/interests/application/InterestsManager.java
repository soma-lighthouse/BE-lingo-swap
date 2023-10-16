package com.lighthouse.lingoswap.interests.application;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.interests.dto.TranslatedCodeDto;
import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;
import com.lighthouse.lingoswap.member.dto.InterestsFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class InterestsManager {

    private final InterestsRepository interestsRepository;
    private final MessageSourceManager messageSourceManager;

    public ResponseDto<InterestsFormResponse> readInterestsForm() {
        List<Interests> interests = interestsRepository.findAll();
        InterestsFormResponse interestsFormResponse = new InterestsFormResponse(
                interests.stream().collect(groupingBy(
                                Interests::getInterestsCategory,
                                mapping(Interests::getName, toList())
                        ))
                        .entrySet().stream()
                        .map(e -> new CategoryInterestsMapDto(
                                new TranslatedCodeDto(e.getKey(), messageSourceManager.translate(e.getKey())),
                                e.getValue().stream().map(v -> new TranslatedCodeDto(v, messageSourceManager.translate(v))).toList())
                        )
                        .toList());
        return ResponseDto.success(interestsFormResponse);
    }

}
