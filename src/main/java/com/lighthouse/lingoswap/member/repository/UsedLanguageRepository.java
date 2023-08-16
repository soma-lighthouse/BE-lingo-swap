package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedLanguageRepository extends JpaRepository<UsedLanguage, Long> {

    List<UsedLanguage> findByMember(Member member);
}
