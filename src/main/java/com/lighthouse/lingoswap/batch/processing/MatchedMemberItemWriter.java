package com.lighthouse.lingoswap.batch.processing;

import com.lighthouse.lingoswap.match.entity.MatchedMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MatchedMemberItemWriter implements ItemWriter<List<MatchedMember>> {

    private final EntityManagerFactory entityManagerFactory;

    public MatchedMemberItemWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void write(Chunk<? extends List<MatchedMember>> chunk) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            for (List<MatchedMember> matchedMembers : chunk) {
                for (MatchedMember matchedMember : matchedMembers) {
                    entityManager.persist(matchedMember);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
