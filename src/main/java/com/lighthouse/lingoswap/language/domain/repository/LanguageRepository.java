package com.lighthouse.lingoswap.language.domain.repository;

import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.language.exception.LanguageNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByCode(final String code);

    default Language getByCode(final String code) {
        return findByCode(code)
                .orElseThrow(LanguageNotFoundException::new);
    }

    List<Language> findAllByCodeIn(final List<String> codes);

}
