package com.lighthouse.lingoswap.country.application;

import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import com.lighthouse.lingoswap.member.dto.CodeNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryManager {

    private final CountryRepository countryRepository;
    private final MessageSource messageSource;

    public CountryFormResponse readCountryForm() {
        return CountryFormResponse.from(
                countryRepository.findAll().stream().map(this::toTranslatedCodeDto).toList());
    }

    private CodeNameDto toTranslatedCodeDto(final Country country) {
        return CodeNameDto.builder()
                .code(country.getCode())
                .name(messageSource.getMessage(country.getCode(), null, LocaleContextHolder.getLocale()))
                .build();
    }

}
