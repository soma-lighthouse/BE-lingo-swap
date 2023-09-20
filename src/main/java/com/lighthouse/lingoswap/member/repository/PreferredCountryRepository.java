package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreferredCountryRepository extends JpaRepository<PreferredCountry, Long> {

    @Query("select p from PreferredCountry p join fetch p.country where p.member.id = :id")
    List<PreferredCountry> findAllByMemberIdWithCountry(Long id);

    List<PreferredCountry> findByMember(Member member);
}