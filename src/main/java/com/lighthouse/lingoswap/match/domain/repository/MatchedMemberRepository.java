package com.lighthouse.lingoswap.match.domain.repository;

import com.lighthouse.lingoswap.match.domain.model.MatchedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchedMemberRepository extends JpaRepository<MatchedMember, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM matched_member as mm WHERE mm.from_member_id = :memberId", nativeQuery = true)
    void deletePreviousMatchedMember(@Param("memberId") final Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
                INSERT INTO matched_member(from_member_id, to_member_id)
                SELECT :memberId, sub.mm_id
                FROM 
                (
                         SELECT
                            m.id AS mm_id,
                            IF(m.region in (:preferredCountryCodes), 20, 0) AS score
                        FROM member m
                        LEFT JOIN preferred_country pc ON m.id = pc.member_id
                        WHERE m.id != :memberId

                        UNION ALL

                        SELECT
                            m.id AS mm_id,
                            IF(i.category_id IN (:categoryIds) ,10 ,0) AS score
                        FROM member m
                        LEFT JOIN preferred_interests pi ON m.id = pi.member_id
                        LEFT JOIN interests i ON pi.interests_id = i.id
                        WHERE m.id != :memberId
                        
                ) AS sub

                GROUP BY sub.mm_id
                ORDER BY SUM(sub.score)
            """, nativeQuery = true)
    void saveMatchedMembersWithPreferences(
            @Param("memberId") final Long memberId,
            @Param("preferredCountryCodes") final List<String> preferredCountryCodes,
            @Param("categoryIds") final List<Long> preferredInterests);

}
