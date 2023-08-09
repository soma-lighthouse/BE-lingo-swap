package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.member.dto.MemberProfileResponse;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredCountry;
import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsService preferredInterestsService;

    public ResponseDto<MemberProfileResponse> read(final Long memberId) {
        Member member = memberService.findByIdWithRegionAndUsedLanguage(memberId);
        List<UsedLanguage> usedLanguages = member.getUsedLanguages();
        List<PreferredCountry> preferredCountries = preferredCountryService.findAllByMemberIdWithCountry(memberId);
        List<PreferredInterests> preferredInterests = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(memberId);
        return ResponseDto.<MemberProfileResponse>builder()
                .code("20000")
                .message("Successfully user matched")
                .data(MemberProfileResponse.of(member, usedLanguages, preferredCountries, preferredInterests))
                .build();
    }
}
