package com.lighthouse.lingoswap.country.application;

import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CountryManager {

    private final CountryRepository countryRepository;
    private final MessageService messageService;

    public CountryFormResponse readForm() {
        return CountryFormResponse.from(
                countryRepository.findAll().stream().map(c -> messageService.toTranslatedCountryCodeNameDto(c.getCode())).toList());
    }
    
}
