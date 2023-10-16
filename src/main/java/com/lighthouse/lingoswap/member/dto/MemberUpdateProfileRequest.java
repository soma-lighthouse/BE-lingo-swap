package com.lighthouse.lingoswap.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateProfileRequest(@NotNull String description, @NotNull String profileImageUri) {

}
