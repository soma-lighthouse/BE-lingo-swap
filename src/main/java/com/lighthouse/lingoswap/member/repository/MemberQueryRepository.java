package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lighthouse.lingoswap.member.entity.QCategory.category;
import static com.lighthouse.lingoswap.member.entity.QInterests.interests;
import static com.lighthouse.lingoswap.member.entity.QMember.member;
import static com.lighthouse.lingoswap.member.entity.QPreferredInterests.preferredInterests;
import static com.lighthouse.lingoswap.member.entity.QUsedLanguage.usedLanguage;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Member> findAllById(List<Long> ids) {
        return queryFactory
                .selectFrom(member)
                .join(member.region)
                .fetchJoin()
                .leftJoin(member.usedLanguages, usedLanguage)
                .fetchJoin()
                .leftJoin(usedLanguage.language)
                .fetchJoin()
                .where(member.id.in(ids))
                .fetch();
    }

    public List<Member> findById(Long id) {
        return queryFactory
                .selectFrom(member)
                .join(member.region)
                .fetchJoin()
                .leftJoin(member.usedLanguages, usedLanguage)
                .fetchJoin()
                .leftJoin(usedLanguage.language)
                .fetchJoin()
                .leftJoin(preferredInterests)
                .fetchJoin()
                .leftJoin(interests)
                .fetchJoin()
                .leftJoin(category)
                .fetchJoin()
                .where(member.id.eq(id))
                .fetch();
    }
}
