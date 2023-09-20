package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Country;
import com.lighthouse.lingoswap.member.repository.CountryQueryRepository;
import com.lighthouse.lingoswap.member.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryQueryRepository countryQueryRepository;

    public Country findById(Integer id) {
        return countryRepository.findById(id).orElseThrow(() -> new RuntimeException("국가가 없습니다"));

    }

    public Country findCountryByCode(String code) {
        return countryRepository.findCountryByCode(code).orElseThrow(() -> new RuntimeException("국가 코드가 존재하지 않습니다"));
    }

    public List<Country> findAllByCodes(List<String> codes) {
        return countryRepository.findAllByCodeIn(codes);
    }

    public List<String> findAllCode() {
        return countryRepository.findAllCode();
    }
}
