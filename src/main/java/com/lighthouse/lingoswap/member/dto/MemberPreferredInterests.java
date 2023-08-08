package com.lighthouse.lingoswap.member.dto;

import java.util.List;

public record MemberPreferredInterests(String category, List<String> interests) {

    public static MemberPreferredInterests of(final String category, final List<String> interests) {
        return new MemberPreferredInterests(category, interests);
    }
}
