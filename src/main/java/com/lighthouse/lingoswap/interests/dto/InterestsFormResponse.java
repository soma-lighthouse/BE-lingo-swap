package com.lighthouse.lingoswap.interests.dto;

import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;

import java.util.List;

public record InterestsFormResponse(List<CategoryInterestsMapDto> interestsForm) {

    public static InterestsFormResponse from(final List<CategoryInterestsMapDto> categoryInterestsMapDtos) {
        return new InterestsFormResponse(categoryInterestsMapDtos);
    }

}
