package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.member.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
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
