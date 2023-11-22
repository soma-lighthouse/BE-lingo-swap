package com.lighthouse.lingoswap.auth.dto;

import com.lighthouse.lingoswap.member.domain.model.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record MemberCreateRequest(@NotNull LocalDate birthday,
                                  @NotBlank String name,
                                  @NotNull @Email String email,
                                  @NotNull Gender gender,
                                  String description,
                                  String region,
                                  @NotEmpty @Size(max = 5) List<@NotBlank String> preferredCountries,
                                  @NotEmpty List<@NotBlank String> preferredInterests) {

    @Builder
    public static MemberCreateRequest of(final LocalDate birthday,
                                         final String name,
                                         final String email,
                                         final Gender gender,
                                         final String description,
                                         final String region,
                                         final List<String> preferredCountries,
                                         final List<String> preferredInterests) {
        return new MemberCreateRequest(
                birthday,
                name,
                email,
                gender,
                description,
                region,
                preferredCountries,
                preferredInterests);
    }

}
