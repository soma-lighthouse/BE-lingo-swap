package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.common.message.MessageSourceManager;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.language.domain.repository.LanguageRepository;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.member.domain.repository.MemberRepository;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.preferredcountry.domain.model.PreferredCountry;
import com.lighthouse.lingoswap.preferredcountry.domain.repository.PreferredCountryRepository;
import com.lighthouse.lingoswap.preferredinterests.domain.model.PreferredInterests;
import com.lighthouse.lingoswap.preferredinterests.domain.repository.PreferredInterestsRepository;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import com.lighthouse.lingoswap.usedlanguage.domain.repository.UsedLanguageRepository;
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
    private final PreferredCountryRepository preferredCountryRepository;
    private final PreferredInterestsRepository preferredInterestsRepository;
    private final LanguageRepository languageRepository;
    private final UsedLanguageRepository usedLanguageRepository;
    private final InterestsRepository interestsRepository;
    private final S3Service s3Service;
    private final CloudFrontService cloudFrontService;
    private final MessageSourceManager messageSourceManager;

    @Transactional(readOnly = true)
    public MemberProfileResponse readProfile(final String uuid) {
        Member member = memberRepository.getByUuid(uuid);
        List<CodeNameDto> preferredCountries = preferredCountryRepository.findAllByMember(member)
                .stream()
                .map(c -> new CodeNameDto(c.getCode(), messageSourceManager.translate(c.getCode())))
                .toList();
        List<UsedLanguage> usedLanguages = usedLanguageRepository.findAllByMember(member);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsRepository.findAllByMemberWithInterestsAndCategory(member));
        List<CategoryInterestsMapDto> preferredInterests = interestsMap.entrySet()
                .stream()
                .map(entry -> CategoryInterestsMapDto.builder()
                        .category(new CodeNameDto(entry.getKey(), messageSourceManager.translate(entry.getKey())))
                        .interests(entry.getValue()
                                .stream()
                                .map(v -> new CodeNameDto(v, messageSourceManager.translate(v)))
                                .toList())
                        .build())
                .toList();
        return MemberProfileResponse.builder()
                .member(member)
                .profileImageUrl(cloudFrontService.addEndpoint(member.getProfileImageUrl()))
                .currentDate(LocalDate.now())
                .region(CodeNameDto.of(member.getRegion(), messageSourceManager.translate(member.getRegion())))
                .preferredCountries(preferredCountries)
                .usedLanguages(usedLanguages)
                .preferredInterests(preferredInterests)
                .build();
    }

    private Map<String, List<String>> groupInterestsByCategory(final List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(groupingBy(PreferredInterests::getInterestsCategory, mapping(PreferredInterests::getInterestsName, toList())));
    }

    public MemberPreferenceResponse readPreference(final String uuid) {
        Member member = memberRepository.getByUuid(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsRepository.findAllByMemberWithInterestsAndCategory(member));
        List<UsedLanguage> usedLanguages = usedLanguageRepository.findAllByMember(member);

        return MemberPreferenceResponse.builder()
                .preferredCountries(preferredCountryRepository.findAllByMember(member).stream()
                        .map(p -> new CodeNameDto(p.getCode(), messageSourceManager.translate(p.getCode()))).toList())
                .usedLanguages(usedLanguages.stream().map(UsedLanguageDto::from).toList())
                .preferredInterests(interestsMap.entrySet().stream()
                        .map(entry -> CategoryInterestsMapDto.of(new CodeNameDto(entry.getKey(), messageSourceManager.translate(entry.getKey())),
                                entry.getValue().stream()
                                        .map(v -> new CodeNameDto(v, messageSourceManager.translate(v))).toList())).toList())
                .build();
    }

    public MemberPreSignedUrlResponse createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        URL preSignedUrl = s3Service.generatePresignedUrl(memberPreSignedUrlRequest.key());
        return MemberPreSignedUrlResponse.from(preSignedUrl);
    }

    public void updatePreference(final String uuid, final MemberPreferenceRequest memberRequest) {
        Member member = memberRepository.getByUuid(uuid);
        List<String> currentInterests = preferredInterestsRepository.findAllByMemberWithInterestsAndCategory(member).stream().map(PreferredInterests::getInterestsName).toList();
        List<String> currentCountries = preferredCountryRepository.findAllByMember(member).stream().map(PreferredCountry::getCode).toList();

        List<UsedLanguage> usedLanguages = usedLanguageRepository.findAllByMember(member);
        List<UsedLanguageInfoDto> currentLanguages = usedLanguages.stream().map(UsedLanguageInfoDto::from).toList();

        updatePreferredCountries(member, memberRequest, currentCountries);
        updateUsedLanguages(member, memberRequest, currentLanguages);
        updatePreferredInterests(member, memberRequest, currentInterests);
    }

    private void updatePreferredCountries(final Member member, final MemberPreferenceRequest memberRequest, List<String> currentCountries) {
        List<String> additionalCountryCodes = memberRequest.preferredCountries().stream()
                .filter(preferredCountry -> !currentCountries.contains(preferredCountry))
                .toList();
        List<String> deletedCountryCodes = currentCountries.stream()
                .filter(currentCountry -> !memberRequest.preferredCountries().contains(currentCountry))
                .toList();

        if (!additionalCountryCodes.isEmpty()) {
            List<PreferredCountry> additionalPreferredCountries = countryRepository.findAllByCodeIn(additionalCountryCodes).stream()
                    .map(a -> new PreferredCountry(member, a))
                    .toList();
            preferredCountryRepository.saveAll(additionalPreferredCountries);
        }

        if (!deletedCountryCodes.isEmpty()) {
            preferredCountryRepository.deleteAllByCountryIn(countryRepository.findAllByCodeIn(deletedCountryCodes));
        }
    }

    private void updateUsedLanguages(final Member member, final MemberPreferenceRequest memberRequest, List<UsedLanguageInfoDto> currentLanguages) {
        List<UsedLanguageInfoDto> additionalUsedLanguageInfoDtos = new ArrayList<>();
        List<UsedLanguageInfoDto> deletedUsedLanguageInfoDtos = new ArrayList<>();
        currentLanguages.stream()
                .filter(currentLanguage -> !memberRequest.usedLanguages().contains(currentLanguage))
                .forEach(deletedUsedLanguageInfoDtos::add);

        memberRequest.usedLanguages().stream()
                .filter(usedLanguageInfo -> !currentLanguages.contains(usedLanguageInfo))
                .forEach(additionalUsedLanguageInfoDtos::add);

        if (!deletedUsedLanguageInfoDtos.isEmpty()) {
            usedLanguageRepository.deleteAllByLanguageIn(languageRepository.findAllByCodeIn(deletedUsedLanguageInfoDtos.stream()
                    .map(UsedLanguageInfoDto::code).toList()));
        }
        if (!additionalUsedLanguageInfoDtos.isEmpty()) {
            List<UsedLanguage> additionalUsedLanguages = additionalUsedLanguageInfoDtos.stream()
                    .map(dto -> new UsedLanguage(member, languageRepository.getLanguageByCode(dto.code()), dto.level())).toList();
            usedLanguageRepository.saveAll(additionalUsedLanguages);
        }
    }

    private void updatePreferredInterests(final Member member, final MemberPreferenceRequest memberRequest, List<String> currentInterests) {
        List<String> additionalPreferredInterestNames = new ArrayList<>();
        List<String> deletedPreferredInterestNames = new ArrayList<>();
        List<String> requestInterests = new ArrayList<>();
        for (PreferredInterestsInfoDto info : memberRequest.preferredInterests()) {
            requestInterests.addAll(info.interests());
        }
        currentInterests.stream()
                .filter(currentInterest -> !requestInterests.contains(currentInterest))
                .forEach(deletedPreferredInterestNames::add);
        requestInterests.stream()
                .filter(requestInterest -> !currentInterests.contains(requestInterest))
                .forEach(additionalPreferredInterestNames::add);

        if (!deletedPreferredInterestNames.isEmpty()) {
            preferredInterestsRepository.deleteAllByInterestsIn(interestsRepository.findAllByNameIn(deletedPreferredInterestNames));
        }
        if (!additionalPreferredInterestNames.isEmpty()) {
            List<PreferredInterests> additionalPreferredInterests = additionalPreferredInterestNames.stream()
                    .map(name -> new PreferredInterests(member, interestsRepository.getByName(name))).toList();
            preferredInterestsRepository.saveAll(additionalPreferredInterests);
        }
    }

    public void updateProfile(final String uuid, final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        Member member = memberRepository.getByUuid(uuid);
        member.changeDescription(memberUpdateProfileRequest.description());
        member.changeProfileImageUrl(memberUpdateProfileRequest.profileImageUrl());
    }

}
