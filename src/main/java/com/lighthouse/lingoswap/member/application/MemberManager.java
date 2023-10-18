package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.domain.model.Country;
import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.preferredinterests.application.PreferredInterestsManager;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberManager {

    private final MemberRepository memberRepository;
    private final CountryRepository countryRepository;
    private final PreferredCountryService preferredCountryService;
    private final PreferredInterestsManager preferredInterestsManager;
    private final LanguageService languageService;
    private final UsedLanguageService usedLanguageService;
    private final InterestsService interestsService;
    private final S3Service s3Service;
    private final CloudFrontService cloudFrontService;
    private final MessageSourceManager messageSourceManager;

    public ResponseDto<MemberProfileResponse> read(final String uuid) {
        Member member = memberRepository.getByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        List<UsedLanguage> usedLanguages = member.getMemberUsedLanguages();
        return ResponseDto.success(
                new MemberProfileResponse(
                        member.getUuid(),
                        cloudFrontService.addEndpoint(member.getProfileImageUrl()),
                        member.getName(),
                        member.calculateAge(LocalDate.now()),
                        member.getDescription(),
                        new CodeNameDto(member.getRegion(), messageSourceManager.translate(member.getRegion())),
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

    public ResponseDto<MemberPreferenceResponse> readPreference(final String uuid) {
        Member member = memberRepository.getByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        return ResponseDto.success(new MemberPreferenceResponse(preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(c -> new CodeNameDto(c.getCountry().getCode(), messageSourceManager.translate(c.getCountry().getCode()))).toList(),
                member.getMemberUsedLanguages().stream().map(UsedLanguageDto::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(new CodeNameDto(entry.getKey(), messageSourceManager.translate(entry.getKey())),
                        entry.getValue().stream().map(v -> new CodeNameDto(v, messageSourceManager.translate(v))).toList())).toList()));
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        URL preSignedUrl = s3Service.generatePresignedUrl(memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    public ResponseDto<Object> updatePreference(final String uuid, final MemberPreferenceRequest memberRequest) {
        Member member = memberRepository.getByUuidWithRegionAndUsedLanguage(uuid);
        List<String> currentInterests = preferredInterestsManager.findAllByMemberIdWithInterestsAndCategory(member.getId()).stream().map(PreferredInterests::getInterestsName).toList();
        List<String> currentCountries = preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(p -> p.getCountry().getCode()).toList();
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
            List<PreferredCountry> additionalPreferredCountries = countryRepository.findAllByCodeIn(additionalCountryCodes).stream()
                    .map(a -> new PreferredCountry(member, a))
                    .toList();
            preferredCountryService.saveAll(additionalPreferredCountries);
        }

        if (!deletedCountryCodes.isEmpty()) {
            preferredCountryService.deleteByCountryCodeIn(countryRepository.findAllByCodeIn(deletedCountryCodes));
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
            usedLanguageService.deleteByLanguageCodeIn(languageService.findAllByCodes(deletedUsedLanguageInfos.stream()
                    .map(UsedLanguageInfo::code).toList()));
        }
        if (!additionalUsedLanguageInfos.isEmpty()) {
            List<UsedLanguage> additionalUsedLanguages = additionalUsedLanguageInfos.stream()
                    .map(a -> new UsedLanguage(member, languageService.findLanguageByCode(a.code()), a.level())).toList();
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
            List<PreferredInterests> additionalPreferredInterests = additionalPreferredInterestNames.stream()
                    .map(a -> new PreferredInterests(member, interestsService.findByName(a))).toList();
            preferredInterestsManager.saveAll(additionalPreferredInterests);
        }
    }

    public ResponseDto<CountryFormResponse> readCountryForm() {
        List<Country> country = countryRepository.findAll();
        return ResponseDto.success(new CountryFormResponse(country.stream()
                .map(c -> new CodeNameDto(c.getCode(), messageSourceManager.translate(c.getCode()))).toList()));
    }

    public ResponseDto<LanguageFormResponse> readLanguageForm() {
        List<Language> languages = languageService.findAll();
        List<CodeNameDto> dto = languages.stream().map(l -> new CodeNameDto(l.getCode(), l.getName())).toList();
        return ResponseDto.success(new LanguageFormResponse(dto));
    }

    public ResponseDto<Object> updateProfile(final String uuid, final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        Member member = memberRepository.getByUuid(uuid);
        member.changeDescription(memberUpdateProfileRequest.description());
        member.changeProfileImageUrl(memberUpdateProfileRequest.profileImageUrl());
        return ResponseDto.success(null);
    }

}
