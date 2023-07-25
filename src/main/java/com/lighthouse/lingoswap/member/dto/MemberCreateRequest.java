package com.lighthouse.lingoswap.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberCreateRequest(@Email(message = "Invalid email format") @NotNull String email) {
}
