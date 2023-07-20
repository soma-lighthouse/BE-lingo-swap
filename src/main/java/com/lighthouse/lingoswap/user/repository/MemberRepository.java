package com.lighthouse.lingoswap.user.repository;

import com.lighthouse.lingoswap.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

}
