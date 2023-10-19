package com.lighthouse.lingoswap.interests.dto;

import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;

import java.util.List;

public record InterestsFormResponse(List<CategoryInterestsMapDto> categoryInterestsMapDtos) {

}
