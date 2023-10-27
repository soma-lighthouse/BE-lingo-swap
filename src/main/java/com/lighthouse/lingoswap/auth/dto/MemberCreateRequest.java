package com.lighthouse.lingoswap.auth.dto;

import com.lighthouse.lingoswap.member.domain.model.Gender;
import com.lighthouse.lingoswap.member.dto.PreferredInterestsInfoDto;
import com.lighthouse.lingoswap.member.dto.UsedLanguageInfoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record MemberCreateRequest(@NotBlank String uuid,
                                  @NotBlank String profileImageUrl,
                                  @NotNull LocalDate birthday,
                                  @NotBlank String name,
                                  @NotNull @Email String email,
                                  @NotNull Gender gender,
                                  @NotNull Integer age,
                                  @NotBlank String description,
                                  @NotBlank String region,
                                  @NotEmpty @Size(max = 5) List<@NotBlank String> preferredCountries,
                                  @NotEmpty @Size(max = 5) List<@Valid UsedLanguageInfoDto> usedLanguages,
                                  @NotEmpty List<@Valid PreferredInterestsInfoDto> preferredInterests) {

    @Builder
    public static MemberCreateRequest of(final String uuid,
                                         final String profileImageUrl,
                                         final LocalDate birthday,
                                         final String name,
                                         final String email,
                                         final Gender gender,
                                         final Integer age,
                                         final String description,
                                         final String region,
                                         final List<String> preferredCountries,
                                         final List<UsedLanguageInfoDto> usedLanguages,
                                         final List<PreferredInterestsInfoDto> preferredInterests) {
        return new MemberCreateRequest(uuid,
                profileImageUrl,
                birthday,
                name,
                email,
                gender,
                age,
                description,
                region,
                preferredCountries,
                usedLanguages,
                preferredInterests);
    }

}
