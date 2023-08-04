package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Category;
import com.lighthouse.lingoswap.member.entity.Country;
import com.lighthouse.lingoswap.member.repository.CategoryRepository;
import com.lighthouse.lingoswap.member.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    public Country findById(Integer id) {
        Country country = countryRepository.findById(id).get();
        return country;
    }
}