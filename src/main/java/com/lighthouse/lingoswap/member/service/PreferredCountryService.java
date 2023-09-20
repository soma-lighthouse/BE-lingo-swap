package com.lighthouse.lingoswap.member.service;

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

    List<PreferredCountry> findAllByMemberIdWithCountry(final Long memberId) {
        return preferredCountryRepository.findAllByMemberIdWithCountry(memberId);
    }


    public void save(final PreferredCountry preferredCountry) {
        preferredCountryRepository.save(preferredCountry);
    }

    public List<PreferredCountry> findByMember(Member fromMember) {
        return preferredCountryRepository.findByMember(fromMember);
    }
}
