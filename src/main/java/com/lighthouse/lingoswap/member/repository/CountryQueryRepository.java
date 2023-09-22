package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.entity.QCountry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CountryQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QCountry country = QCountry.country;

    @Autowired
    public CountryQueryRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}

