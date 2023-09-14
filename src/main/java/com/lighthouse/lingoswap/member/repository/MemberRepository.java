package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.region left join fetch m.usedLanguages u left join fetch u.language where m.id in :ids")
    List<Member> findAllByIdsWithRegionAndUsedLanguage(List<Long> ids);

    @Query("select m from Member m join fetch m.region left join fetch m.usedLanguages u left join fetch u.language where m.id = :id")
    Member findByIdWithRegionAndUsedLanguage(Long id);

    Optional<Member> findByAuthUsername(String username);

    Optional<Member> findByAuthUuid(String uuid);
}
