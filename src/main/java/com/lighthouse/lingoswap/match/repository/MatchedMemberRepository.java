package com.lighthouse.lingoswap.match.repository;

import com.lighthouse.lingoswap.match.entity.MatchedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchedMemberRepository extends JpaRepository<MatchedMember, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM filtered_member as mm WHERE mm.from_member_id = :memberId", nativeQuery = true)
    void deletePreviousFilteredMember(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
                INSERT INTO filtered_member(from_member_id, to_member_id)
                SELECT :memberId, sub.mm_id
                FROM 
                (
                         SELECT
                            mm.to_member_id AS mm_id,
                            IF(m.region_id in (:preferredCountryIds), 20,0) AS score
                        FROM matched_member mm
                        JOIN Member m ON mm.to_member_id = m.id
                        LEFT JOIN preferred_country pc ON m.id = pc.member_id
                        WHERE mm.from_member_id = :memberId

                        UNION ALL

                        SELECT
                            mm.to_member_id AS mm_id,
                            IF(ul.language_id IN (:languageIds), 5, 0) AS score
                        FROM matched_Member mm
                        JOIN Member m ON mm.to_member_id = m.id
                        JOIN used_language ul ON m.id = ul.member_id
                        WHERE mm.from_member_id = :memberId

                        UNION ALL

                        SELECT
                            mm.to_member_id AS mm_id,
                            IF(i.category_id IN (:categoryIds) ,1 ,0) AS score
                        FROM matched_member mm
                        JOIN Member m ON mm.to_member_id = m.id
                        JOIN preferred_interests pi ON m.id = pi.member_id
                        JOIN interests i ON pi.interests_id = i.id
                        WHERE mm.from_member_id = :memberId
                        
                ) AS sub

                GROUP BY sub.mm_id
                ORDER BY SUM(sub.score) DESC
            """, nativeQuery = true)
    void saveFilteredMembersWithPreferences(
            @Param("memberId") Long memberId,
            @Param("preferredCountryIds") List<Long> preferredCountryIds,
            @Param("languageIds") List<Long> preferredLanguages,
            @Param("categoryIds") List<Long> preferredInterests);
}
