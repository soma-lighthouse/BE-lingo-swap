package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.common.dto.ResponseDto;
import com.lighthouse.lingoswap.common.dto.SendbirdCreateUserRequest;
import com.lighthouse.lingoswap.common.service.SendbirdService;
import com.lighthouse.lingoswap.infra.service.DistributionService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.entity.*;
import com.lighthouse.lingoswap.question.dto.MyQuestionDetail;
import com.lighthouse.lingoswap.question.dto.MyQuestionListResponse;
import com.lighthouse.lingoswap.question.entity.Category;
import com.lighthouse.lingoswap.question.service.CategoryService;
import com.lighthouse.lingoswap.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
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
    private final SendbirdService sendbirdService;
    private final S3Service s3Service;
    private final DistributionService distributionService;
    private final MessageSource messageSource;
    private final CategoryService categoryService;
    private final QuestionService questionService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.profile.prefix}")
    private String profileKeyPrefix;

    private static Map<String, List<String>> groupInterestsByCategory(List<PreferredInterests> preferredInterests) {
        return preferredInterests.stream().collect(
                groupingBy(
                        p -> p.getInterests().getCategory().getName(),
                        mapping(p -> p.getInterests().getName(), toList())
                )
        );
    }

    public ResponseDto<MemberProfileResponse> read(final Long memberId, Locale locale) {
        Member member = memberService.findByIdWithRegionAndUsedLanguage(memberId);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(memberId));
        List<UsedLanguage> usedLanguages = member.getUsedLanguages();
        return ResponseDto.success(new MemberProfileResponse(member.getId(), distributionService.generateUri(profileKeyPrefix + member.getProfileImageUri()), member.getName(), member.calculateAge(), member.getDescription(), member.getRegion().getCode(),
                preferredCountryService.findAllByMemberIdWithCountry(memberId).stream().map(c -> new CountryFormResponseUnit(c.getCountry().getCode(), messageSource.getMessage(c.getCountry().getCode(), null, locale))).toList(),
                usedLanguages.stream().map(MemberUsedLanguage::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(new CategoryDto(entry.getKey(), messageSource.getMessage(entry.getKey(), null, locale)),
                        entry.getValue().stream().map(v -> new InterestsDto(v, messageSource.getMessage(v, null, locale))).toList())).toList()));
    }

    public ResponseDto<MemberPreferenceResponse> getPreference(Long userId, Locale locale) {
        Member member = memberService.findByIdWithRegionAndUsedLanguage(userId);
        Map<String, List<String>> interestsMap = groupInterestsByCategory(preferredInterestsService.findAllByMemberIdWithInterestsAndCategory(userId));
        return ResponseDto.success(new MemberPreferenceResponse(preferredCountryService.findAllByMemberIdWithCountry(userId).stream().map(c -> new CountryFormResponseUnit(c.getCountry().getCode(), messageSource.getMessage(c.getCountry().getCode(), null, locale))).toList(),
                member.getUsedLanguages().stream().map(MemberUsedLanguage::from).toList(),
                interestsMap.entrySet().stream().map(entry -> MemberPreferredInterests.of(new CategoryDto(entry.getKey(), messageSource.getMessage(entry.getKey(), null, locale)),
                        entry.getValue().stream().map(v -> new InterestsDto(v, messageSource.getMessage(v, null, locale))).toList())).toList()));
    }

    public ResponseDto<MemberPreSignedUrlResponse> createPreSignedUrl(final MemberPreSignedUrlRequest memberPreSignedUrlRequest) {
        String preSignedUrl = s3Service.generatePreSignedUrl(bucketName, profileKeyPrefix + memberPreSignedUrlRequest.key());
        return ResponseDto.success(MemberPreSignedUrlResponse.from(preSignedUrl));
    }

    @Transactional
    public ResponseDto<Object> create(MemberRequest memberRequest) {
        Country country = countryService.findCountryByCode(memberRequest.region());
        Member member = new Member(
                memberRequest.birthday(),
                memberRequest.name(),
                memberRequest.description(),
                memberRequest.profileImageUri(),
                memberRequest.gender(),
                null,
                country
        );
        memberService.save(member);

        savePreferredCountries(member, memberRequest.preferredCountries());
        saveUsedLanguages(member, memberRequest.usedLanguages());
        savePreferredInterests(member, memberRequest.preferredInterests());

        SendbirdCreateUserRequest sendbirdCreateUserRequest = new SendbirdCreateUserRequest(String.valueOf(member.getId()), member.getName(), member.getProfileImageUri());
        sendbirdService.createUser(sendbirdCreateUserRequest);
        return ResponseDto.success(null);
    }

    @Transactional
    public void savePreferredCountries(Member member, List<String> preferredCountries) {
        preferredCountries.stream()
                .map(countryService::findCountryByCode)
                .map(preferredCountry -> new PreferredCountry(member, preferredCountry))
                .forEach(preferredCountryService::save);
    }

    @Transactional
    public void saveUsedLanguages(Member member, List<UsedLanguageInfo> usedLanguageInfos) {
        usedLanguageInfos.stream()
                .map(lang -> {
                    Language language = languageService.findLanguageByCode(lang.code());
                    return new UsedLanguage(member, language, lang.level());
                })
                .forEach(usedLanguageService::save);
    }

    @Transactional
    public void savePreferredInterests(Member member, List<PreferredInterestsInfo> preferredInterestsInfos) {
        preferredInterestsInfos.stream()
                .flatMap(userInterestsByDto -> userInterestsByDto.interests().stream())
                .map(interestsService::findByName)
                .map(interest -> new PreferredInterests(member, interest))
                .forEach(preferredInterestsService::save);
    }

    @Transactional
    public ResponseDto<InterestsFormResponse> readInterestsForm(Locale locale) {
        List<Category> categories = categoryService.findAll();
        return ResponseDto.success(new InterestsFormResponse(categories.stream().map(c ->
                new InterestsFormResponseUnit(c.getName(), messageSource.getMessage(c.getName(), null, locale), c.getInterests().stream().map(i ->
                        InterestsUnit.of(i, messageSource.getMessage(i.getName(), null, locale))).toList())).toList()));
    }

    public ResponseDto<CountryFormResponse> readCountryForm(Locale locale) {
        List<String> countryCodes = countryService.findAllCode();
        return ResponseDto.success(new CountryFormResponse(countryCodes.stream().map(code ->
                new CountryFormResponseUnit(code, messageSource.getMessage(code, null, locale))).toList()));
    }

    public ResponseDto<LanguageFormResponse> readLanguageForm() {
        return ResponseDto.success(languageService.getAllLanguages());
    }

    public ResponseDto<Object> patch(Long userId, MemberRequest memberRequest) {
        Member member = memberService.findById(userId);
        member.updateMember(memberRequest.birthday(), memberRequest.name(), memberRequest.description(), memberRequest.profileImageUri(),
                memberRequest.gender(), countryService.findCountryByCode(memberRequest.region()));
        memberService.save(member);
        return ResponseDto.success(null);
    }

    public ResponseDto<MyQuestionListResponse> getMyQuestion(Long userId) {
        Member member = memberService.findById(userId);
        return ResponseDto.success(new MyQuestionListResponse(questionService.searchMyQuestion(member).stream().map(MyQuestionDetail::from).toList()));
    }
}
