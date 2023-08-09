package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreferredInterestsRepository extends JpaRepository<PreferredInterests, Long> {

    @Query("select p from PreferredInterests p join fetch p.interests i join fetch i.category where p.member.id = :id")
    List<PreferredInterests> findAllByMemberIdWithInterestsAndCategory(Long id);
}
