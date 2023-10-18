package com.lighthouse.lingoswap.preferredcountry.domain.repository;

import com.lighthouse.lingoswap.country.domain.repository.CountryNotFoundException;
import com.lighthouse.lingoswap.member.domain.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByCode(final String code);

    default Country getByCode(final String code) {
        return findByCode(code)
                .orElseThrow(CountryNotFoundException::new);
    }

    boolean existsByCode(final String code);

    default void validateExistsByCode(final String code) {
        if (existsByCode(code)) {
            throw new CountryNotFoundException();
        }
    }

    List<Country> findAllByCodeIn(final List<String> codes);

}
