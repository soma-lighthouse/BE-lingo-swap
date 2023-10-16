package com.lighthouse.lingoswap.usedlanguage.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsedLanguageRepository extends JpaRepository<UsedLanguage, Long> {

    List<UsedLanguage> findByMember(Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM UsedLanguage u WHERE u.language IN :languages")
    void deleteAllByLanguageIn(@Param("languages") List<Language> languages);
    
}
