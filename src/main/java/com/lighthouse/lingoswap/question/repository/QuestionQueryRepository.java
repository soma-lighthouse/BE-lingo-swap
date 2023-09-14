package com.lighthouse.lingoswap.question.repository;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.question.entity.Question;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lighthouse.lingoswap.question.entity.QQuestion.question;

@Repository
public class QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public QuestionQueryRepository(final EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public SliceDto<Question> findQuestionsByCategoryId(final Long categoryId, final Long nextId, final int pageSize) {
        List<Question> questions = queryFactory
                .selectFrom(question)
                .where(
                        question.category.id.eq(categoryId),
                        questionIdLt(nextId)
                )
                .orderBy(question.id.desc())
                .limit(pageSize + 1L)
                .fetch();

        Long lastId = removeLastAndReturnNextIdByPageSize(questions, pageSize);
        return new SliceDto<>(questions, lastId);
    }

    private BooleanExpression questionIdLt(final Long nextId) {
        return nextId == null ? null : question.id.lt(nextId);
    }

    private Long removeLastAndReturnNextIdByPageSize(final List<Question> questions, final int pageSize) {
        Long lastId = -1L;
        if (questions.size() > pageSize) {
            questions.remove(pageSize);
            lastId = questions.get(pageSize - 1).getId();
        }
        return lastId;
    }
}
