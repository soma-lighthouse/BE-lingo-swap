package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Country;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredCountry;
import com.lighthouse.lingoswap.member.repository.PreferredCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PreferredCountryService {

    private final PreferredCountryRepository preferredCountryRepository;

    List<PreferredCountry> findAllByMemberIdWithCountry(Long id) {
        return preferredCountryRepository.findAllByMemberIdWithCountry(id);
    }


    public void save(PreferredCountry preferredCountry) {
        preferredCountryRepository.save(preferredCountry);
    }

    public void deleteByCountryCodeIn(List<Country> countries) {
        preferredCountryRepository.deleteAllByCountryIn(countries);
    }

    public void saveAll(List<PreferredCountry> preferredCountries) {
        preferredCountryRepository.saveAll(preferredCountries);
    }

    public List<PreferredCountry> findByMember(Member member) {
        return preferredCountryRepository.findAllByMember(member);
    }
}
