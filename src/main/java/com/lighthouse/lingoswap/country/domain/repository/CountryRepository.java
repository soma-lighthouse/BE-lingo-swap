package com.lighthouse.lingoswap.country.domain.repository;

import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.country.exception.CountryNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByCode(final String code);

    default Country getByCode(final String code) {
        return findByCode(code)
                .orElseThrow(() -> new CountryNotFoundException(code));
    }
    
    List<Country> findAllByCodeIn(final List<String> codes);

}
