package com.lighthouse.lingoswap.country.application;

import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryManager {

    private final CountryRepository countryRepository;
    private final MessageService messageService;

    public CountryFormResponse readCountryForm() {
        return CountryFormResponse.from(
                countryRepository.findAll().stream().map(c -> messageService.toTranslatedCodeNameDto(c.getCode())).toList());
    }

}
