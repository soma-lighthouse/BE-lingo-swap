package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.member.entity.PreferredInterests;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public record MemberProfileResponse(Long id,
                                    String profileImage,
                                    String name,
                                    int age,
                                    String description,
                                    String region,
                                    List<String> preferredCountries,
                                    List<MemberUsedLanguage> languages,
                                    List<MemberPreferredInterests> preferredInterests) {

//    public static MemberProfileResponse from(Member member) {
//        Map<String, List<String>> interestsMap = groupInterestsByCategory(member.getPreferredInterests());
//        return new MemberProfileResponse(
//                member.getId(),
//                member.getProfileImage(),
//                member.getName(),
//                member.calculateAge(),
//                member.getDescription(),
//                member.getRegion().getCode(),
//                member.getPreferredCountries().stream().map(p -> p.getCountry().getCode()).toList(),
//                member.getUsedLanguages().stream().map(MemberUsedLanguage::from).toList(),
//                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(entry.getKey(), entry.getValue())).toList()
//        );
//    }

    private static Map<String, List<String>> groupInterestsByCategory(List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(
                groupingBy(
                        p -> p.getInterests().getCategory().getName(),
                        mapping(p -> p.getInterests().getName(), toList())
                )
        );
    }
}