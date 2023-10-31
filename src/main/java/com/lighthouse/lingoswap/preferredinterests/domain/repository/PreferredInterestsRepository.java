package com.lighthouse.lingoswap.preferredinterests.domain.repository;

import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreferredInterestsRepository extends JpaRepository<PreferredInterests, Long> {

    @Query("select p from PreferredInterests p where p.member.member = :member")
    List<PreferredInterests> findAllByMember(@Param("member") final Member member);

    @Modifying
    @Transactional
    @Query("delete from PreferredInterests p where p.interests in :interests")
    void deleteAllByInterestsIn(@Param("interests") final List<Interests> interests);

}
