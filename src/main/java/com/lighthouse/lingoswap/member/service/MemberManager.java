package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.service.MessageSourceService;
import com.lighthouse.lingoswap.infra.dto.SendbirdUpdateUserProfileUrlRequest;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.infra.service.SendbirdService;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.entity.*;
import com.lighthouse.lingoswap.question.dto.MyQuestionDetail;
import com.lighthouse.lingoswap.question.dto.MyQuestionsResponse;
import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.service.CategoryService;
import com.lighthouse.lingoswap.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

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
    private final S3Service s3Service;
    private final DistributionService distributionService;
    private final MessageSourceService messageSourceService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final SendbirdService sendbirdService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.profile.prefix}")
    private String profileKeyPrefix;

    public ResponseDto<MemberProfileResponse> read(final String uuid) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        List<UsedLanguage> usedLanguages = member.getUsedLanguages();
        return ResponseDto.success(
                new MemberProfileResponse(
                        member.getAuthDetails().getUuid(),
                        distributionService.generateUri(member.getProfileImageUri()),
                        member.getName(),
                        member.calculateAge(),
                        member.getDescription(),
                        member.getRegion().getCode(),
                        preferredCountryService.findAllByMemberIdWithCountry(member.getId())
                                .stream()
                                .map(c -> new CodeNameDto(c.getCountry().getCode(), messageSourceService.translate(c.getCountry().getCode())))
                                .toList(),
                        usedLanguages.stream().map(UsedLanguageDto::from).toList(),
                        interestsMap.entrySet()
                                .stream()
                                .map(entry -> MemberPreferredInterests.of(
                                        new CodeNameDto(entry.getKey(), messageSourceService.translate(entry.getKey())),
                                        entry.getValue()
                                                .stream()
                                                .map(v -> new CodeNameDto(v, messageSourceService.translate(v)))
                                                .toList()))
                                .toList()));
    }

    public ResponseDto<MemberPreferenceResponse> getPreference(final String uuid) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(member.getId()));
        return ResponseDto.success(new MemberPreferenceResponse(preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(c -> new CodeNameDto(c.getCountry().getCode(), messageSourceService.translate(c.getCountry().getCode()))).toList(),
                member.getUsedLanguages().stream().map(UsedLanguageDto::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(new CodeNameDto(entry.getKey(), messageSourceService.translate(entry.getKey())),
                        entry.getValue().stream().map(v -> new CodeNameDto(v, messageSourceService.translate(v))).toList())).toList()));
    }

    private Map<String, List<String>> groupInterestsByCategory(final List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(
                groupingBy(
                        p -> p.getInterests().getCategory().getName(),
                        mapping(p -> p.getInterests().getName(), toList())
                )
        );
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    @Transactional
    public ResponseDto<Object> patchPreference(final String uuid, final MemberPreferenceRequest memberRequest) {
        Member member = memberService.findByUuidWithRegionAndUsedLanguage(uuid);
        List<String> currentInterests = preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(member.getId()).stream().map(p -> p.getInterests().getName()).toList();
        List<String> currentCountries = preferredCountryService.findAllByMemberIdWithCountry(member.getId()).stream().map(c -> c.getCountry().getCode()).toList();
        List<UsedLanguageInfo> currentLanguages = member.getUsedLanguages().stream().map(UsedLanguageInfo::from).toList();

        updatePreferredCountries(member, memberRequest, currentCountries);
        updateUsedLanguages(member, memberRequest, currentLanguages);
        updatePreferredInterests(member, memberRequest, currentInterests);

        return ResponseDto.success(null);
    }

    @Transactional
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

    @Transactional
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

    @Transactional
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
            preferredInterestsService.deleteByInterestsNameIn(interestsService.findAllByNames(deletedPreferredInterestNames));
        }
        if (!additionalPreferredInterestNames.isEmpty()) {
            List<PreferredInterests> additionalPreferredInterests = additionalPreferredInterestNames.stream().map(a -> new PreferredInterests(member, interestsService.findByName(a))).toList();
            preferredInterestsService.saveAll(additionalPreferredInterests);
        }
    }

    @Transactional
    public ResponseDto<InterestsFormResponse> readInterestsForm() {
        List<Category> categories = categoryService.findAllWithCategory();
        List<InterestsFormResponseUnit> interestsFormResponseUnit = categories.stream().map(this::mapCategoryWithInterests).toList();
        return ResponseDto.success(new InterestsFormResponse(interestsFormResponseUnit));
    }

    private InterestsFormResponseUnit mapCategoryWithInterests(final Category category) {
        CodeNameDto resolvedCategory = new CodeNameDto(category.getName(), messageSourceService.translate(category.getName()));
        List<CodeNameDto> interestsDto = category.getInterests().stream().map(
                i -> new CodeNameDto(i.getName(), messageSourceService.translate(i.getName()))
        ).toList();
        return new InterestsFormResponseUnit(resolvedCategory, interestsDto);
    }

    public ResponseDto<CountryFormResponse> readCountryForm() {
        List<String> countryCodes = countryService.findAllCode();
        return ResponseDto.success(new CountryFormResponse(countryCodes.stream().map(code ->
                new CodeNameDto(code, messageSourceService.translate(code))).toList()));
    }

    public ResponseDto<LanguageFormResponse> readLanguageForm() {
        List<Language> languages = languageService.findAll();
        List<CodeNameDto> dto = languages.stream().map(l -> new CodeNameDto(l.getCode(), l.getName())).toList();
        return ResponseDto.success(new LanguageFormResponse(dto));
    }

    @Transactional
    public ResponseDto<Object> patch(final String uuid, final MemberUpdateProfileRequest memberUpdateProfileRequest) {
        Member member = memberService.findByUuid(uuid);
        if (!member.getProfileImageUri().equals(memberUpdateProfileRequest.profileImageUri())) {
            SendbirdUpdateUserProfileUrlRequest sendbirdUpdateUserProfileUrlRequest = new SendbirdUpdateUserProfileUrlRequest(distributionService.generateUri(memberUpdateProfileRequest.profileImageUri()));
            sendbirdService.updateUserProfileUrl(uuid, sendbirdUpdateUserProfileUrlRequest);
        }
        member.updateMember(memberUpdateProfileRequest.description(), memberUpdateProfileRequest.profileImageUri());
        memberService.save(member);
        return ResponseDto.success(null);
    }

    public ResponseDto<MyQuestionsResponse> getMyQuestion(final String uuid) {
        Member member = memberService.findByUuid(uuid);
        return ResponseDto.success(new MyQuestionsResponse(questionService.searchMyQuestion(member).stream().map(MyQuestionDetail::from).toList()));
    }
}
