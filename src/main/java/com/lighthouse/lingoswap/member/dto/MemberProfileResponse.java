package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredCountry;
import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public record MemberProfileResponse(Long id,
                                    String profileImageUri,
                                    String name,
                                    int age,
                                    String description,
                                    String region,
                                    List<String> preferredCountries,
                                    List<MemberUsedLanguage> languages,
                                    List<MemberPreferredInterests> preferredInterests) {

    public static MemberProfileResponse of(Member member,
                                           String profileImageUri,
                                           List<UsedLanguage> usedLanguages,
                                           List<PreferredCountry> preferredCountries,
                                           List<PreferredInterests> preferredInterests) {
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterests);
        return new MemberProfileResponse(
                member.getId(),
                profileImageUri,
                member.getName(),
                member.calculateAge(),
                member.getDescription(),
                member.getRegion().getCode(),
                preferredCountries.stream().map(p -> p.getCountry().getCode()).toList(),
                usedLanguages.stream().map(MemberUsedLanguage::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(entry.getKey(), entry.getValue())).toList()
        );
    }

    private static Map<String, List<String>> groupInterestsByCategory(List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(
                groupingBy(
                        p -> p.getInterests().getCategory().getName(),
                        mapping(p -> p.getInterests().getName(), toList())
                )
        );
    }
}
