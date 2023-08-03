package com.lighthouse.lingoswap.match.repository;

import com.lighthouse.lingoswap.common.dto.SliceDto;
import com.lighthouse.lingoswap.match.entity.MatchedMember;
import com.lighthouse.lingoswap.member.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lighthouse.lingoswap.match.entity.QMatchedMember.matchedMember;

@Repository
public class MatchQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MatchQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public SliceDto<Member> search(final Long fromMemberId, final Long nextMemberId, int pageSize) {
        List<MatchedMember> matchedMembers = queryFactory
                .selectFrom(matchedMember)
                .join(matchedMember.toMember)
                .fetchJoin()
                .where(
                        matchedMember.fromMember.id.eq(fromMemberId),
                        matchedMemberIdLt(nextMemberId)
                )
                .orderBy(matchedMember.id.desc())
                .limit(pageSize + 1L)
                .fetch();

        Long lastMemberId = removeLastAndReturnNextIdByPageSize(matchedMembers, pageSize);
        List<Member> members = matchedMembers.stream().map(MatchedMember::getToMember).toList();
        return new SliceDto<>(members, lastMemberId);
    }

    private BooleanExpression matchedMemberIdLt(Long memberId) {
        return memberId == null ? null : matchedMember.id.lt(memberId);
    }

    private Long removeLastAndReturnNextIdByPageSize(final List<MatchedMember> matchedMembers, final int pageSize) {
        Long lastMemberId = -1L;
        if (matchedMembers.size() > pageSize) {
            matchedMembers.remove(pageSize);
            lastMemberId = matchedMembers.get(pageSize - 1).getId();
        }
        return lastMemberId;
    }
}
