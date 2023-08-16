package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findCountryByCode(String code);
}
