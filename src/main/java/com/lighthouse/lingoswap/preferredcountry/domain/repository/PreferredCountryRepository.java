package com.lighthouse.lingoswap.preferredcountry.domain.repository;

import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreferredCountryRepository extends JpaRepository<PreferredCountry, Long> {

    @Query("SELECT p FROM PreferredCountry p WHERE p.member.member = :member")
    List<PreferredCountry> findAllByMember(@Param("member") final Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM PreferredCountry p WHERE p.country.country IN :countries")
    void deleteAllByCountryIn(@Param("countries") final List<Country> countries);

}
