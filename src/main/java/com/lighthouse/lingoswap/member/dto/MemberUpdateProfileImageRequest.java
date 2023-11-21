package com.lighthouse.lingoswap.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateProfileImageRequest(@NotNull String profileImageUri) {

    public static MemberUpdateProfileImageRequest of(final String profileImageUri) {
        return new MemberUpdateProfileImageRequest(profileImageUri);
    }

}
