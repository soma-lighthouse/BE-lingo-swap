package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.dto.MemberLanguageResponse;
import com.lighthouse.lingoswap.member.dto.MemberProfileResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lighthouse.lingoswap.member.entity.QMember.member;

@Slf4j
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MemberProfileResponse> searchById(Long id, int page) {
        return queryFactory
                .select(Projections.fields(MemberProfileResponse.class, member.profileImage,                        member.name,                        member.description,                        member.region,
                        languages))
                .from(member)
                .join()
    }
}
