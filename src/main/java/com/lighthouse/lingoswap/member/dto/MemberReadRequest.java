package com.lighthouse.lingoswap.member.dto;


import jakarta.validation.constraints.NotNull;

public record MemberReadRequest(@NotNull Long id) {
}
