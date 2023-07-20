package com.lighthouse.lingoswap.user.dto;


import jakarta.validation.constraints.NotNull;

public record ReadDetailsRequest(@NotNull Long id) {

}
