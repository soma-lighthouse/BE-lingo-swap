package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
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

    private static final String BUCKET_NAME = "lingoswap";
    private static final String PROFILE_KEY_PREFIX = "profiles/";

    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsService preferredInterestsService;
    private final S3Service s3Service;

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

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(Long memberId) {
        String preSignedUrl = s3Service.generatePreSignedUrl(BUCKET_NAME, PROFILE_KEY_PREFIX + "test.txt");
        return ResponseDto.<MemberPreSignedUrlResponse>builder()
                .code("20000")
                .message("Successfully generated")
                .data(MemberPreSignedUrlResponse.from(preSignedUrl))
                .build();
    }
}
