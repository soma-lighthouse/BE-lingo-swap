package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferredInterests(CategoryDto category, List<InterestsDto> interests) {

    public static MemberPreferredInterests of(final CategoryDto category, final List<InterestsDto> interests) {
        return new MemberPreferredInterests(category, interests);
    }
}
