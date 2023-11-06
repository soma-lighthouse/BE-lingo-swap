package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.common.service.MessageService;
import com.lighthouse.lingoswap.common.util.TimeHolder;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
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

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberRepository memberRepository;
    private final CountryRepository countryRepository;
    private final PreferredCountryRepository preferredCountryRepository;
    private final PreferredInterestsRepository preferredInterestsRepository;
    private final LanguageRepository languageRepository;
    private final UsedLanguageRepository usedLanguageRepository;
    private final InterestsRepository interestsRepository;
    private final CloudFrontService cloudFrontService;
    private final MessageService messageService;
    private final TimeHolder timeHolder;

    public MemberProfileResponse readProfile(final String uuid) {
        Member member = memberRepository.getByUuid(uuid);
        List<CodeNameDto> preferredCountries = toTranslatedPreferredCountryDto(preferredCountryRepository.findAllByMember(member));
        List<UsedLanguage> usedLanguages = usedLanguageRepository.findAllByMember(member);
        List<CategoryInterestsMapDto> preferredInterests = toTranslatedPreferredInterestsDto(preferredInterestsRepository.findAllByMember(member));
        return MemberProfileResponse.builder()
                .uuid(member.getUuid())
                .profileImageUri(cloudFrontService.addEndpoint(member.getProfileImageUri()))
                .name(member.getName())
                .age(member.calculateAge(timeHolder.now()))
                .description(member.getDescription())
                .region(messageService.toTranslatedCountryCodeNameDto(member.getRegion()))
                .preferredCountries(preferredCountries)
                .usedLanguages(usedLanguages.stream().map(UsedLanguageDto::from).toList())
                .preferredInterests(preferredInterests)
                .build();
    }

    private List<CodeNameDto> toTranslatedPreferredCountryDto(final List<PreferredCountry> preferredCountries) {
        return preferredCountries.stream()
                .map(c -> messageService.toTranslatedCountryCodeNameDto(c.getCode()))
                .toList();
    }

    private List<CategoryInterestsMapDto> toTranslatedPreferredInterestsDto(final List<PreferredInterests> preferredInterests) {
        Map<String, List<String>> interestsMap = preferredInterests.stream()
                .collect(groupingBy(PreferredInterests::getInterestsCategory, mapping(PreferredInterests::getInterestsName, toList())));

        return interestsMap.entrySet()
                .stream()
                .map(entry -> CategoryInterestsMapDto.builder()
                        .category(messageService.toTranslatedCategoryCodeNameDto(entry.getKey()))
                        .interests(entry.getValue()
                                .stream()
                                .map(messageService::toTranslatedInterestsCodeNameDto)
                                .toList())
                        .build())
                .toList();
    }

    public MemberPreferenceResponse readPreference(final String uuid) {
        Member member = memberRepository.getByUuid(uuid);
        List<CodeNameDto> preferredCountries = toTranslatedPreferredCountryDto(preferredCountryRepository.findAllByMember(member));
        List<UsedLanguage> usedLanguages = usedLanguageRepository.findAllByMember(member);
        List<CategoryInterestsMapDto> preferredInterests = toTranslatedPreferredInterestsDto(preferredInterestsRepository.findAllByMember(member));
        return MemberPreferenceResponse.builder()
                .preferredCountries(preferredCountries)
                .usedLanguages(usedLanguages.stream().map(UsedLanguageDto::from).toList())
                .preferredInterests(preferredInterests)
                .build();
    }

    @Transactional
    public void updateProfile(final String uuid, final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        Member member = memberRepository.getByUuid(uuid);
        member.changeDescription(memberUpdateProfileRequest.description());
        member.changeProfileImageUri(memberUpdateProfileRequest.profileImageUri());
    }

    @Transactional
    public void updatePreference(final String uuid, final MemberUpdatePreferenceRequest memberRequest) {
        Member member = memberRepository.getByUuid(uuid);
        updatePreferredCountries(member, memberRequest.preferredCountries());
        updateUsedLanguages(member, memberRequest.usedLanguages());
        updatePreferredInterests(member, memberRequest.preferredInterests());
    }

    private void updatePreferredCountries(final Member member, final List<String> preferredCountries) {
        List<String> currentCountries = preferredCountryRepository.findAllByMember(member).stream().map(PreferredCountry::getCode).toList();

        List<String> additionalCountryCodes = preferredCountries.stream()
                .filter(p -> !currentCountries.contains(p))
                .toList();
        List<String> deletedCountryCodes = currentCountries.stream()
                .filter(c -> !preferredCountries.contains(c))
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

    private void updateUsedLanguages(final Member member, final List<UsedLanguageInfoDto> usedLanguageInfos) {
        List<UsedLanguageInfoDto> currentLanguages = usedLanguageRepository.findAllByMember(member).stream().map(UsedLanguageInfoDto::from).toList();

        List<UsedLanguageInfoDto> additionalUsedLanguageInfos = usedLanguageInfos.stream()
                .filter(u -> !currentLanguages.contains(u))
                .toList();
        List<UsedLanguageInfoDto> deletedUsedLanguageInfos = currentLanguages.stream()
                .filter(c -> !usedLanguageInfos.contains(c))
                .toList();

        if (!deletedUsedLanguageInfos.isEmpty()) {
            usedLanguageRepository.deleteAllByLanguageIn(languageRepository.findAllByCodeIn(deletedUsedLanguageInfos.stream()
                    .map(UsedLanguageInfoDto::code).toList()));
        }

        if (!additionalUsedLanguageInfos.isEmpty()) {
            List<UsedLanguage> additionalUsedLanguages = additionalUsedLanguageInfos.stream()
                    .map(dto -> new UsedLanguage(member, languageRepository.getByCode(dto.code()), dto.level())).toList();
            usedLanguageRepository.saveAll(additionalUsedLanguages);
        }
    }

    private void updatePreferredInterests(final Member member, final List<PreferredInterestsInfoDto> preferredInterestsInfos) {
        preferredInterestsRepository.deleteAllByMember(member);
        List<String> names = preferredInterestsInfos.stream().flatMap(p -> p.interests().stream()).toList();
        List<Interests> interests = interestsRepository.findAllByNameIn(names);
        List<PreferredInterests> preferredInterests = interests.stream().map(i -> new PreferredInterests(member, i)).toList();
        preferredInterestsRepository.saveAll(preferredInterests);
    }

}
