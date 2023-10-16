package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferredInterests(CodeNameDto category, List<CodeNameDto> interests) {

    public static MemberPreferredInterests of(final CodeNameDto category, final List<CodeNameDto> interests) {
        return new MemberPreferredInterests(category, interests);
    }

}
