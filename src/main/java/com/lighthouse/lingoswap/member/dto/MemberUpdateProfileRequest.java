package com.lighthouse.lingoswap.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record MemberUpdateProfileRequest(@NotNull String description, @NotNull String profileImageUrl) {

    @Builder
    public static MemberUpdateProfileRequest of(final String description, final String profileImageUrl) {
        return new MemberUpdateProfileRequest(description, profileImageUrl);
    }

}
