package com.lighthouse.lingoswap.member.repository;

import com.lighthouse.lingoswap.member.dto.CountryFormResponseUnit;
import com.lighthouse.lingoswap.member.entity.QCountry;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QCountry country = QCountry.country;

    @Autowired
    public CountryQueryRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<CountryFormResponseUnit> findAllCountry() {
        return queryFactory
                .select(Projections.fields(CountryFormResponseUnit.class, country.code, country.name))
                .from(country)
                .fetch();
    }
}

