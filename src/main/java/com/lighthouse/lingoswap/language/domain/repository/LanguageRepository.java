package com.lighthouse.lingoswap.language.domain.repository;

import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.language.exception.LanguageNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findLanguageByCode(final String code);

    default Language getLanguageByCode(final String code) {
        return findLanguageByCode(code)
                .orElseThrow(LanguageNotFoundException::new);
    }

    List<Language> findAllByCodeIn(final List<String> codes);

}
