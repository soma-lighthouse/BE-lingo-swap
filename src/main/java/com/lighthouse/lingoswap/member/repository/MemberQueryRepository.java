package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lighthouse.lingoswap.member.entity.QUsedLanguage.usedLanguage;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UsedLanguage> findUsedLanguagesWithJoinIn(List<Long> memberIds) {
        return queryFactory
                .selectFrom(usedLanguage)
                .join(usedLanguage.language)
                .fetchJoin()
                .where(usedLanguage.member.id.in(memberIds))
                .fetch();
    }
}
