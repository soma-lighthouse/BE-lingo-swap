package com.lighthouse.lingoswap.usedlanguage.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findLanguageByCode(String code);

    List<Language> findAllByCodeIn(List<String> codes);

}
