package com.lighthouse.lingoswap.member.domain.repository;

import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

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

    void deleteByAuthDetailsUsername(final String username);

}
