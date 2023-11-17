package com.lighthouse.lingoswap.usedlanguage.domain.repository;

import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsedLanguageRepository extends JpaRepository<UsedLanguage, Long> {

    @Query("SELECT u FROM UsedLanguage u WHERE u.member.member = :member")
    List<UsedLanguage> findAllByMember(@Param("member") final Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM UsedLanguage u WHERE u.languageInfo.language IN :languages")
    void deleteAllByLanguageIn(@Param("languages") final List<Language> languages);

}
