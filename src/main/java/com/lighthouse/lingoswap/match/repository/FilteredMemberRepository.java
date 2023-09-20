package com.lighthouse.lingoswap.match.repository;

import com.lighthouse.lingoswap.match.entity.FilteredMember;
import com.lighthouse.lingoswap.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilteredMemberRepository extends JpaRepository<FilteredMember, Long> {

    Slice<FilteredMember> findByFromMember(Member member, Pageable pageable);
}
