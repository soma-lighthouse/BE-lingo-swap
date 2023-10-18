package com.lighthouse.lingoswap.member.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.region left join fetch m.memberUsedLanguages.usedLanguages u left join fetch u.language where m.authDetails.uuid = :uuid")
    Optional<Member> findByUuidWithRegionAndUsedLanguage(@Param("uuid") final String uuid);

    default Member getByUuidWithRegionAndUsedLanguage(final String uuid) {
        return findByUuidWithRegionAndUsedLanguage(uuid)
                .orElseThrow(MemberNotFoundException::new);
    }

    Optional<Member> findByAuthDetailsUsername(final String username);

    default Member getByUsername(final String username) {
        return findByAuthDetailsUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }

    Optional<Member> findByAuthDetailsUuid(final String uuid);

    default Member getByUuid(final String uuid) {
        return findByAuthDetailsUuid(uuid)
                .orElseThrow(MemberNotFoundException::new);
    }

}
