package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredinterests.application.PreferredInterestsManager;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberManager {

    private final MemberService memberService;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsManager preferredInterestsManager;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final UsedLanguageService usedLanguageService;
    private final InterestsService interestsService;
    private final S3Service s3Service;
    private final DistributionService distributionService;
    private final MessageSourceManager messageSourceManager;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.profile.prefix}")
    private String profileKeyPrefix;

    public ResponseDto<MemberProfileResponse> read(final String uuid) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        List<UsedLanguage> usedLanguages = member.getMemberUsedLanguages();
        return ResponseDto.success(
                new MemberProfileResponse(
                        member.getUuid(),
                        distributionService.generateUri(member.getProfileImageUri()),
                        member.getName(),
                        member.calculateAge(LocalDate.now()),
                        member.getDescription(),
                        new CodeNameDto(member.getRegionCode(), messageSourceManager.translate(member.getRegionCode())),
                        preferredCountryService.findAllByMemberIdWithCountry(member.getId())
                                .stream()
                                .map(c -> new CodeNameDto(c.getCountryCode(), messageSourceManager.translate(c.getCountryCode())))
                                .toList(),
                        usedLanguages.stream().map(UsedLanguageDto::from).toList(),
                        interestsMap.entrySet()
                                .stream()
                                .map(entry -> MemberPreferredInterests.of(
                                        new CodeNameDto(entry.getKey(), messageSourceManager.translate(entry.getKey())),
                                        entry.getValue()
                                                .stream()
                                                .map(v -> new CodeNameDto(v, messageSourceManager.translate(v)))
                                                .toList()))
                                .toList()));
    }

    private Map<String, List<String>> groupInterestsByCategory(final List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(groupingBy(PreferredInterests::getInterestsCategory, mapping(PreferredInterests::getInterestsName, toList())));
    }

    public ResponseDto<MemberPreferenceResponse> getPreference(final String uuid) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        return ResponseDto.success(new MemberPreferenceResponse(preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(c -> new CodeNameDto(c.getCountry().getCode(), messageSourceManager.translate(c.getCountry().getCode()))).toList(),
                member.getMemberUsedLanguages().stream().map(UsedLanguageDto::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(new CodeNameDto(entry.getKey(), messageSourceManager.translate(entry.getKey())),
                        entry.getValue().stream().map(v -> new CodeNameDto(v, messageSourceManager.translate(v))).toList())).toList()));
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    public ResponseDto<Object> updatePreference(final String uuid, final MemberPreferenceRequest memberRequest) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        List<String> currentInterests = preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()).stream().map(PreferredInterests::getInterestsName).toList();
        List<String> currentCountries = preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(c -> c.getCountry().getCode()).toList();
        List<UsedLanguageInfo> currentLanguages = member.getMemberUsedLanguages().stream().map(UsedLanguageInfo::from).toList();

        updatePreferredCountries(member, memberRequest, currentCountries);
        updateUsedLanguages(member, memberRequest, currentLanguages);
        updatePreferredInterests(member, memberRequest, currentInterests);

        return ResponseDto.success(null);
    }

    public void updatePreferredCountries(final Member member, final MemberPreferenceRequest memberRequest, List<String> currentCountries) {
        List<String> additionalCountryCodes = new ArrayList<>();
        List<String> deletedCountryCodes = new ArrayList<>();
        currentCountries.stream()
                .filter(currentCountry -> !memberRequest.preferredCountries().contains(currentCountry))
                .forEach(deletedCountryCodes::add);

        memberRequest.preferredCountries().stream()
                .filter(preferredCountry -> !currentCountries.contains(preferredCountry))
                .forEach(additionalCountryCodes::add);

        if (!additionalCountryCodes.isEmpty()) {
            List<PreferredCountry> additionalPreferredCountries = countryService.findAllByCodes(additionalCountryCodes).stream().map(a -> new PreferredCountry(member, a)).toList();
            preferredCountryService.saveAll(additionalPreferredCountries);
        }

        if (!deletedCountryCodes.isEmpty()) {
            preferredCountryService.deleteByCountryCodeIn(countryService.findAllByCodes(deletedCountryCodes));
        }
    }

    public void updateUsedLanguages(final Member member, final MemberPreferenceRequest memberRequest, List<UsedLanguageInfo> currentLanguages) {
        List<UsedLanguageInfo> additionalUsedLanguageInfos = new ArrayList<>();
        List<UsedLanguageInfo> deletedUsedLanguageInfos = new ArrayList<>();
        currentLanguages.stream()
                .filter(currentLanguage -> !memberRequest.usedLanguages().contains(currentLanguage))
                .forEach(deletedUsedLanguageInfos::add);

        memberRequest.usedLanguages().stream()
                .filter(usedLanguageInfo -> !currentLanguages.contains(usedLanguageInfo))
                .forEach(additionalUsedLanguageInfos::add);

        if (!deletedUsedLanguageInfos.isEmpty()) {
            usedLanguageService.deleteByLanguageCodeIn(languageService.findAllByCodes(deletedUsedLanguageInfos.stream().map(UsedLanguageInfo::code).toList()));
        }
        if (!additionalUsedLanguageInfos.isEmpty()) {
            List<UsedLanguage> additionalUsedLanguages = additionalUsedLanguageInfos.stream().map(a -> new UsedLanguage(member, languageService.findLanguageByCode(a.code()), a.level())).toList();
            usedLanguageService.saveAll(additionalUsedLanguages);
        }
    }

    public void updatePreferredInterests(final Member member, final MemberPreferenceRequest memberRequest, List<String> currentInterests) {
        List<String> additionalPreferredInterestNames = new ArrayList<>();
        List<String> deletedPreferredInterestNames = new ArrayList<>();
        List<String> requestInterests = new ArrayList<>();
        for (PreferredInterestsInfo info : memberRequest.preferredInterests()) {
            requestInterests.addAll(info.interests());
        }
        currentInterests.stream()
                .filter(currentInterest -> !requestInterests.contains(currentInterest))
                .forEach(deletedPreferredInterestNames::add);
        requestInterests.stream()
                .filter(requestInterest -> !currentInterests.contains(requestInterest))
                .forEach(additionalPreferredInterestNames::add);

        if (!deletedPreferredInterestNames.isEmpty()) {
            preferredInterestsManager.deleteByInterestsNameIn(interestsService.findAllByNames(deletedPreferredInterestNames));
        }
        if (!additionalPreferredInterestNames.isEmpty()) {
            List<PreferredInterests> additionalPreferredInterests = additionalPreferredInterestNames.stream().map(a -> new PreferredInterests(member, interestsService.findByName(a))).toList();
            preferredInterestsManager.saveAll(additionalPreferredInterests);
        }
    }

    public ResponseDto<CountryFormResponse> readCountryForm() {
        List<String> countryCodes = countryService.findAllCode();
        return ResponseDto.success(new CountryFormResponse(countryCodes.stream().map(code ->
                new CodeNameDto(code, messageSourceManager.translate(code))).toList()));
    }

    public ResponseDto<LanguageFormResponse> readLanguageForm() {
        List<Language> languages = languageService.findAll();
        List<CodeNameDto> dto = languages.stream().map(l -> new CodeNameDto(l.getCode(), l.getName())).toList();
        return ResponseDto.success(new LanguageFormResponse(dto));
    }

    public ResponseDto<Object> updateProfile(final String uuid, final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        Member member = memberService.findByUuid(uuid);
        member.changeDescription(memberUpdateProfileRequest.description());
        member.changeProfileImageUri(memberUpdateProfileRequest.profileImageUri());
        memberService.save(member);
        return ResponseDto.success(null);
    }

}
