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

    @Query("select p from PreferredInterests p join fetch p.interests i join fetch i.interestsCategory where p.member = :member")
    List<PreferredInterests> findAllByMemberWithInterestsAndCategory(@Param("member") final Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM PreferredInterests p WHERE p.interests IN :interests")
    void deleteAllByInterestsIn(@Param("interests") final List<Interests> interests);

}
