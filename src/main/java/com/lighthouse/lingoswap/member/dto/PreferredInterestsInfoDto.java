package com.lighthouse.lingoswap.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public record PreferredInterestsInfoDto(@NotBlank String category, @NotEmpty @Size(max = 5) List<String> interests) {

    @Builder
    public static PreferredInterestsInfoDto of(final String category, final List<String> interests) {
        return new PreferredInterestsInfoDto(category, interests);
    }

}
