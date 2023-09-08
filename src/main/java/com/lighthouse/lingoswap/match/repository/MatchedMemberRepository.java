package com.lighthouse.lingoswap.match.repository;

import com.lighthouse.lingoswap.match.entity.MatchedMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchedMemberRepository extends JpaRepository<MatchedMember, Long> {


    @Query(value = """
                SELECT sub.mm_id
                FROM 
                (
                    SELECT
                        mm.id AS mm_id,
                        CASE
                            WHEN m.region_id in (:preferredCountryIds) THEN 20 ELSE 0
                        END AS score
                    FROM matched_member mm
                    JOIN Member m ON mm.to_member_id = m.id
                    JOIN preferred_Country pc ON m.id = pc.member_id
                    WHERE mm.from_member_id = :memberId

                    UNION ALL

                    SELECT
                        mm.id AS mm_id,
                        CASE
                            WHEN ul.language_id IN (:languageIds) THEN 5 ELSE 0
                        END AS score
                    FROM matched_Member mm
                    JOIN Member m ON mm.to_member_id = m.id
                    JOIN used_Language ul ON m.id = ul.member_id
                    WHERE mm.from_member_id = :memberId

                    UNION ALL

                    SELECT
                        mm.id AS mm_id,
                        CASE
                            WHEN i.category_id IN (:categoryIds) THEN 1 ELSE 0
                        END AS score
                    FROM matched_member mm
                    JOIN Member m ON mm.to_member_id = m.id
                    JOIN preferred_interests pi ON m.id = pi.member_id
                    JOIN interests i ON pi.interests_id = i.id
                    WHERE mm.from_member_id = :memberId

                ) AS sub

                GROUP BY sub.mm_id
                ORDER BY SUM(sub.score) DESC
            """, nativeQuery = true)
    Slice<Long> findMatchedMembersWithPreferences(
            @Param("memberId") Long memberId,
            @Param("preferredCountryIds") int preferredCountryIds,
            @Param("languageIds") List<Long> preferredLanguages,
            @Param("categoryIds") List<Long> preferredInterests,
            Pageable pageable);
}