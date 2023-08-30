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
        return ResponseDto.<MemberProfileResponse>builder()
                .code("20000")
                .message("Successfully user matched")
                .data(MemberProfileResponse.of(member, distributionService.generateUri(profileKeyPrefix + member.getProfileImageUri()), usedLanguages, preferredCountries, preferredInterests))
                .build();
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.<MemberPreSignedUrlResponse>builder()
                .code("20000")
                .message("Successfully generated")
                .data(MemberPreSignedUrlResponse.from(preSignedUrl))
                .build();
    }

    @Transactional
    public void create(MemberCreateRequest memberCreateRequest) {
        Country country = countryService.findCountryByCode(memberCreateRequest.getRegion());
        Member member = new Member(
                memberCreateRequest.getGender(),
                memberCreateRequest.getBirthday(),
                memberCreateRequest.getName(),
                memberCreateRequest.getDescription(),
                memberCreateRequest.getProfileImageUri(),
                memberCreateRequest.getEmail(),
                country,
                memberCreateRequest.getUuid());
        memberService.save(member);

        savePreferredCountries(member, memberCreateRequest);
        saveUsedLanguages(member, memberCreateRequest);
        savePreferredInterests(member, memberCreateRequest);

        List<UsedLanguage> usedLanguages = usedLanguageService.findByMember(member);
        member.addUsedLanguage(usedLanguages);
        memberService.save(member);

        SendbirdCreateUserRequest sendbirdCreateUserRequest =
                new SendbirdCreateUserRequest(String.valueOf(member.getId()), member.getName(), member.getProfileImageUri());
        sendbirdService.createUser(sendbirdCreateUserRequest);
    }

    public InterestsFormResponse readInterestsForm() {
        return interestsFormService.getAllInterests();
    }

    public CountryFormResponse readCountryForm() {
        return countryService.getAllCountries();
    }

    public LanguageFormResponse readLanguageForm() {
        return languageService.getAllLanguages();
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

        List<String> a = List.of("A", "B");
        List<String> b = List.of("C", "D");
        List<String> c = List.of("E", "F");

        preferredInterestsByDto.stream()
                .peek(userInterestsByDto -> System.out.println("UserInterestsByDto: " + userInterestsByDto))
                .flatMap(userInterestsByDto -> userInterestsByDto.getInterests().stream())
                .peek(interestName -> System.out.println("Interest Name: " + interestName))
                .map(interestsService::findByName)
                .peek(interest -> System.out.println("Interest: " + interest))
                .map(interest -> new PreferredInterests(member, interest))
                .peek(preferredInterests -> System.out.println("PreferredInterests: " + preferredInterests))
                .forEach(preferredInterests -> {
                    System.out.println("Saving PreferredInterests: " + preferredInterests);
                    preferredInterestsService.save(preferredInterests);
                });
    }
}
