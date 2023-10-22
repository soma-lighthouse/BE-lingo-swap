package com.lighthouse.lingoswap.preferredcountry.domain.repository;

import com.lighthouse.lingoswap.common.support.IntegrationTestSupport;
import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CountryType.KOREA;
import static com.lighthouse.lingoswap.common.fixture.CountryType.US;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.user;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PreferredCountryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    PreferredCountryRepository preferredCountryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CountryRepository countryRepository;

    @DisplayName("유저의 선호 국가를 조회한다.")
    @Test
    void findAllByMember() {
        // given
        Member member = memberRepository.save(user());
        Country korea = countryRepository.getByCode(KOREA.getCode());
        Country us = countryRepository.getByCode(US.getCode());
        PreferredCountry preferredKorea = PreferredCountry.builder()
                .member(member)
                .country(korea)
                .build();
        PreferredCountry preferredUs = PreferredCountry.builder()
                .member(member)
                .country(us)
                .build();
        preferredCountryRepository.saveAll(List.of(preferredKorea, preferredUs));

        // when
        List<PreferredCountry> actual = preferredCountryRepository.findAllByMember(member);

        // then
        assertThat(actual).containsExactly(preferredKorea, preferredUs);
    }

    @DisplayName("국가들과 일치하는 선호 국가를 제거한다.")
    @Test
    void deleteAllByCountryIn() {
        // given
        Member member = memberRepository.save(user());
        Country korea = countryRepository.getByCode(KOREA.getCode());
        Country us = countryRepository.getByCode(US.getCode());
        PreferredCountry preferredKorea = PreferredCountry.builder()
                .member(member)
                .country(korea)
                .build();
        PreferredCountry preferredUs = PreferredCountry.builder()
                .member(member)
                .country(us)
                .build();
        preferredCountryRepository.saveAll(List.of(preferredKorea, preferredUs));

        // when
        preferredCountryRepository.deleteAllByCountryIn(List.of(korea, us));

        // then
        List<PreferredCountry> actual = preferredCountryRepository.findAllByMember(member);
        assertThat(actual).isEmpty();
    }

}
