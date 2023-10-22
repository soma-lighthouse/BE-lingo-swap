package com.lighthouse.lingoswap.country.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;

import java.util.List;

public record CountryFormResponse(List<CodeNameDto> countryForm) {

    public static CountryFormResponse from(final List<CodeNameDto> countryForm) {
        return new CountryFormResponse(countryForm);
    }

}
