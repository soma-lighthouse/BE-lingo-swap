package com.lighthouse.lingoswap.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public record MemberUpdatePreferenceRequest(
        @NotEmpty @Size(max = 5) List<@NotBlank String> preferredCountries,
        @NotEmpty @Size(max = 5) List<@Valid UsedLanguageInfoDto> usedLanguages,
        @NotEmpty List<@Valid PreferredInterestsInfoDto> preferredInterests) {

    @Builder
    public static MemberUpdatePreferenceRequest of(final List<String> preferredCountries,
                                                   final List<UsedLanguageInfoDto> usedLanguages,
                                                   final List<PreferredInterestsInfoDto> preferredInterests) {
        return new MemberUpdatePreferenceRequest(preferredCountries, usedLanguages, preferredInterests);
    }

}
