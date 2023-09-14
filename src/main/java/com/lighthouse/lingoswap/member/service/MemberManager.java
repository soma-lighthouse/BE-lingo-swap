package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.auth.entity.Auth;
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

    public void create(String username, MemberCreateRequest memberCreateRequest) {
        Country country = countryService.findCountryByCode(memberCreateRequest.region());
        Auth auth = new Auth(username, memberCreateRequest.uuid(), Role.USER);
        Member member = new Member(
                memberCreateRequest.birthday(),
                memberCreateRequest.name(),
                memberCreateRequest.description(),
                memberCreateRequest.profileImageUri(),
                memberCreateRequest.gender(),
                auth,
                country
        );
        memberService.save(member);

        savePreferredCountries(member, memberCreateRequest.preferredCountries());
        saveUsedLanguages(member, memberCreateRequest.usedLanguages());
        savePreferredInterests(member, memberCreateRequest.preferredInterests());

        SendbirdCreateUserRequest sendbirdCreateUserRequest = new SendbirdCreateUserRequest(auth.getUuid(), member.getName(), member.getProfileImageUri());
        sendbirdService.createUser(sendbirdCreateUserRequest);
    }

    private void savePreferredCountries(Member member, List<String> preferredCountries) {
        preferredCountries.stream()
                .map(countryService::findCountryByCode)
                .map(preferredCountry -> new PreferredCountry(member, preferredCountry))
                .forEach(preferredCountryService::save);
    }

    private void saveUsedLanguages(Member member, List<UsedLanguageInfo> usedLanguageInfos) {
        usedLanguageInfos.stream()
                .map(lang -> {
                    Language language = languageService.findLanguageByCode(lang.code());
                    return new UsedLanguage(member, language, lang.level());
                })
                .forEach(usedLanguageService::save);
    }

    private void savePreferredInterests(Member member, List<PreferredInterestsInfo> preferredInterestsInfos) {
        preferredInterestsInfos.stream()
                .flatMap(userInterestsByDto -> userInterestsByDto.interests().stream())
                .map(interestsService::findByName)
                .map(interest -> new PreferredInterests(member, interest))
                .forEach(preferredInterestsService::save);
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
