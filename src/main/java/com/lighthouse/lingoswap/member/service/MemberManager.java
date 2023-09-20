package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.PreferredCountry;
import com.lighthouse.lingoswap.member.entity.PreferredInterests;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsService preferredInterestsService;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final InterestsFormService interestsFormService;
    private final S3Service s3Service;
    private final DistributionService distributionService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.profile.prefix}")
    private String profileKeyPrefix;

    public ResponseDto<MemberProfileResponse> read(final String uuid) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        List<UsedLanguage> usedLanguages = member.getUsedLanguages();
        List<PreferredCountry> preferredCountries = preferredCountryService.findAllByMemberIdWithCountry(member.getId());
        List<PreferredInterests> preferredInterests = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(member.getId());
        return ResponseDto.success(MemberProfileResponse.of(member, distributionService.generateUri(profileKeyPrefix + member.getProfileImageUri()), usedLanguages, preferredCountries, preferredInterests));
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    public ResponseDto<InterestsFormResponse> readInterestsForm() {
        return ResponseDto.success(interestsFormService.getAllInterests());
    }

    public ResponseDto<CountryFormResponse> readCountryForm() {
        return ResponseDto.success(countryService.getAllCountries());
    }

    public ResponseDto<LanguageFormResponse> readLanguageForm() {
        return ResponseDto.success(languageService.getAllLanguages());
    }
}
