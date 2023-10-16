package com.lighthouse.lingoswap.preferredcountry.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreferredCountryRepository extends JpaRepository<PreferredCountry, Long> {

    @Query("select p from PreferredCountry p join fetch p.country where p.member.id = :id")
    List<PreferredCountry> findAllByMemberIdWithCountry(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM PreferredCountry p WHERE p.country IN :countries")
    void deleteAllByCountryIn(@Param("countries") List<Country> countries);

    List<PreferredCountry> findAllByMember(Member member);

}
