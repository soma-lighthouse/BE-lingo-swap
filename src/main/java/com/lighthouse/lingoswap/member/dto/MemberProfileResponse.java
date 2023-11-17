package com.lighthouse.lingoswap.member.dto;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import lombok.Builder;

import java.util.List;

public record MemberProfileResponse(String uuid,
                                    String profileImageUri,
                                    String name,
                                    String description,
                                    CodeNameDto region,
                                    List<CodeNameDto> preferredCountries,
                                    List<CategoryInterestsMapDto> preferredInterests) {

    @Builder
    public static MemberProfileResponse of(final String uuid,
                                           final String profileImageUri,
                                           final String name,
                                           final String description,
                                           final CodeNameDto region,
                                           final List<CodeNameDto> preferredCountries,
                                           final List<CategoryInterestsMapDto> preferredInterests) {
        return new MemberProfileResponse(
                uuid,
                profileImageUri,
                name,
                description,
                region,
                preferredCountries,
                preferredInterests
        );
    }

}
