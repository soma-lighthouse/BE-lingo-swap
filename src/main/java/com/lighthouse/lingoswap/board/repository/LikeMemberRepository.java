package com.lighthouse.lingoswap.board.repository;

import com.lighthouse.lingoswap.board.entity.LikeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeMemberRepository extends JpaRepository<LikeMember, Long> {

    List<LikeMember> findAllByMemberId(Long memberId);
}
