package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.interests.dto.TranslatedCodeDto;

import java.util.List;

public record CategoryInterestsMapDto(TranslatedCodeDto category, List<TranslatedCodeDto> interests) {

}
