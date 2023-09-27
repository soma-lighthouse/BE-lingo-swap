package com.lighthouse.lingoswap.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReissueRequest(@NotNull @Pattern(regexp = "^[\\w-]+.[\\w-]+.[\\w-]+$") String refreshToken) {

}
