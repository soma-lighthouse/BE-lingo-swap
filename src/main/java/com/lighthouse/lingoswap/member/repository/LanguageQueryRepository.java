package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.dto.LanguageFormResponseUnit;
import com.lighthouse.lingoswap.member.entity.QLanguage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LanguageQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QLanguage language = QLanguage.language;

    @Autowired
    public LanguageQueryRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<LanguageFormResponseUnit> findAllLanguage() {
        return queryFactory
                .select(Projections.fields(LanguageFormResponseUnit.class, language.code, language.name))
                .from(language)
                .fetch();
    }
}
