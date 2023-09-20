package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findCountryByCode(String code);

    @Query("select c.code from Country c")
    List<String> findAllCode();

    List<Country> findAllByCodeIn(List<String> codes);
}
