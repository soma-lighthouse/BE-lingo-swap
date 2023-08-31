package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.service.SendbirdService;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsService preferredInterestsService;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final UsedLanguageService usedLanguageService;
    private final InterestsService interestsService;
    private final InterestsFormService interestsFormService;
    private final SendbirdService sendbirdService;
    private final S3Service s3Service;
    private final DistributionService distributionService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.profile.prefix}")
    private String profileKeyPrefix;

    public ResponseDto<MemberProfileResponse> read(final Long memberId) {
        Member member = memberService.findByIdWithRegionAndUsedLanguage(memberId);
        List<UsedLanguage> usedLanguages = member.getUsedLanguages();
        List<PreferredCountry> preferredCountries = preferredCountryService.findAllByMemberIdWithCountry(memberId);
        List<PreferredInterests> preferredInterests = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(memberId);
        return ResponseDto.success(MemberProfileResponse.of(member, distributionService.generateUri(profileKeyPrefix + member.getProfileImageUri()), usedLanguages, preferredCountries, preferredInterests));
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    @Transactional
    public ResponseDto<Object> create(MemberCreateRequest memberCreateRequest) {
        Country country = countryService.findCountryByCode(memberCreateRequest.getRegion());
        Member member = new Member(
                memberCreateRequest.getGender(),
                memberCreateRequest.getBirthday(),
                memberCreateRequest.getName(),
                memberCreateRequest.getDescription(),
                memberCreateRequest.getProfileImageUri(),
                memberCreateRequest.getEmail(),
                country);
        memberService.save(member);

        savePreferredCountries(member, memberCreateRequest);
        saveUsedLanguages(member, memberCreateRequest);
        savePreferredInterests(member, memberCreateRequest);

        List<UsedLanguage> usedLanguages = usedLanguageService.findByMember(member);
        member.addUsedLanguage(usedLanguages);
        memberService.save(member);

        SendbirdCreateUserRequest sendbirdCreateUserRequest = new SendbirdCreateUserRequest(String.valueOf(member.getId()), member.getName(), member.getProfileImageUri());
        sendbirdService.createUser(sendbirdCreateUserRequest);
        return ResponseDto.success(null);
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

    @Transactional
    public void savePreferredCountries(Member member, MemberCreateRequest memberCreateRequest) {
        List<String> preferredCountries = memberCreateRequest.getPreferredCountries();

        preferredCountries.stream()
                .map(countryService::findCountryByCode)
                .map(preferredCountry -> new PreferredCountry(member, preferredCountry))
                .forEach(preferredCountryService::save);
    }


    @Transactional
    public void saveUsedLanguages(Member member, MemberCreateRequest memberCreateRequest) {
        List<MemberCreateRequest.UsedLanguage> usedLanguages = memberCreateRequest.getUsedLanguages();

        usedLanguages.stream()
                .map(lang -> {
                    Language language = languageService.findLanguageByCode(lang.getCode());
                    return new UsedLanguage(member, language, lang.getLevel());
                })
                .forEach(usedLanguageService::save);
    }

    @Transactional
    public void savePreferredInterests(Member member, MemberCreateRequest memberCreateRequest) {
        List<MemberCreateRequest.PreferredInterests> preferredInterestsByDto = memberCreateRequest.getPreferredInterests();

        preferredInterestsByDto.stream()
                .flatMap(userInterestsByDto -> userInterestsByDto.getInterests().stream())
                .map(interestsService::findByName)
                .map(interest -> new PreferredInterests(member, interest))
                .forEach(preferredInterestsService::save);
    }
}
