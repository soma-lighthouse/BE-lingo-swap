package com.lighthouse.lingoswap.auth.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String idToken) {

}
