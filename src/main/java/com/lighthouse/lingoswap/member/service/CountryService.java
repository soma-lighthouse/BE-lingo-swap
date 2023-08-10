package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Country;
import com.lighthouse.lingoswap.member.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    Country findById(Integer id) {
        Country country = countryRepository.findById(id).get();
        return country;
    }
}
