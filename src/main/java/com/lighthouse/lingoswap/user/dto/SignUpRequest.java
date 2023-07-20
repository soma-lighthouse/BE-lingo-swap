package com.lighthouse.lingoswap.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(@NotNull @Email String email) {

}
