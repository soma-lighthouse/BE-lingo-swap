package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
