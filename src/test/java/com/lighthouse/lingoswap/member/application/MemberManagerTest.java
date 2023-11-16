package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.country.domain.model.Country;
import com.lighthouse.lingoswap.country.domain.repository.CountryRepository;
import com.lighthouse.lingoswap.interests.domain.model.Interests;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.language.domain.model.Language;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static com.lighthouse.lingoswap.common.fixture.CategoryType.FOOD;
import static com.lighthouse.lingoswap.common.fixture.CategoryType.GAME;
import static com.lighthouse.lingoswap.common.fixture.CountryType.*;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static com.lighthouse.lingoswap.common.fixture.LanguageType.ENGLISH;
import static com.lighthouse.lingoswap.common.fixture.LanguageType.KOREAN;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Transactional
class MemberManagerTest extends IntegrationTestSupport {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private PreferredCountryRepository preferredCountryRepository;

    @Autowired
    private UsedLanguageRepository usedLanguageRepository;

    @Autowired
    private PreferredInterestsRepository preferredInterestsRepository;

    @DisplayName("UUID로 유저의 프로필을 조회하면 한국어로 출력한다.")
    @Test
    void readProfile() {
        // given
        LocaleContextHolder.setLocale(Locale.KOREAN);

        Member member = memberRepository.save(user());
        savePreferredCountries(member);
        saveUsedLanguages(member);
        savePreferredInterests(member);

        // when
        MemberProfileResponse actual = memberManager.readProfile(USER_UUID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.uuid()).isEqualTo(USER_UUID);
            softly.assertThat(actual.profileImageUri()).isEqualTo(USER_PROFILE_IMAGE_URI);
            softly.assertThat(actual.name()).isEqualTo(USER_NAME);
            softly.assertThat(actual.description()).isEqualTo(USER_DESCRIPTION);
            softly.assertThat(actual.region().code()).isEqualTo(KOREA.getCode());
            softly.assertThat(actual.region().name()).isEqualTo(KOREA.getKoreanName());
            softly.assertThat(actual.preferredCountries())
                    .extracting("code", "name")
                    .containsExactlyInAnyOrder(
                            tuple(KOREA.getCode(), KOREA.getKoreanName()),
                            tuple(US.getCode(), US.getKoreanName()));
            softly.assertThat(actual.preferredInterests())
                    .containsExactlyInAnyOrder(
                            CategoryInterestsMapDto.builder()
                                    .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                                    .interests(List.of(
                                            CodeNameDto.of(JAPANESE_FOOD.getName(), JAPANESE_FOOD.getKoreanName()),
                                            CodeNameDto.of(CHINESE_FOOD.getName(), CHINESE_FOOD.getKoreanName())))
                                    .build(),
                            CategoryInterestsMapDto.builder()
                                    .category(CodeNameDto.of(GAME.getName(), GAME.getKoreanName()))
                                    .interests(List.of(
                                            CodeNameDto.of(RPG_GAME.getName(), RPG_GAME.getKoreanName()),
                                            CodeNameDto.of(FPS_GAME.getName(), FPS_GAME.getKoreanName())))
                                    .build());
        });
    }

    private void savePreferredCountries(final Member member) {
        Country korea = countryRepository.getByCode(KOREA.getCode());
        Country us = countryRepository.getByCode(US.getCode());
        preferredCountryRepository.saveAll(List.of(
                PreferredCountry.builder().member(member).country(korea).build(),
                PreferredCountry.builder().member(member).country(us).build()));
    }

    private void saveUsedLanguages(final Member member) {
        Language korean = languageRepository.getByCode(KOREAN.getCode());
        Language english = languageRepository.getByCode(ENGLISH.getCode());
        usedLanguageRepository.saveAll(List.of(
                UsedLanguage.builder().member(member).language(korean).level(5).build(),
                UsedLanguage.builder().member(member).language(english).level(3).build()));
    }

    private void savePreferredInterests(final Member member) {
        Interests japaneseFood = interestsRepository.getByName(JAPANESE_FOOD.getName());
        Interests chineseFood = interestsRepository.getByName(CHINESE_FOOD.getName());
        Interests rpgGame = interestsRepository.getByName(RPG_GAME.getName());
        Interests fpsGame = interestsRepository.getByName(FPS_GAME.getName());
        preferredInterestsRepository.saveAll(List.of(
                PreferredInterests.builder().member(member).interests(japaneseFood).build(),
                PreferredInterests.builder().member(member).interests(chineseFood).build(),
                PreferredInterests.builder().member(member).interests(rpgGame).build(),
                PreferredInterests.builder().member(member).interests(fpsGame).build()));
    }

    @DisplayName("UUID로 유저의 선호 내용을 조회하면 한국어로 출력한다.")
    @Test
    void readPreference() {
        // given
        LocaleContextHolder.setLocale(Locale.KOREAN);

        Member member = memberRepository.save(user());
        savePreferredCountries(member);
        saveUsedLanguages(member);
        savePreferredInterests(member);

        // when
        MemberPreferenceResponse actual = memberManager.readPreference(USER_UUID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.preferredCountries())
                    .extracting("code", "name")
                    .containsExactlyInAnyOrder(
                            tuple(KOREA.getCode(), KOREA.getKoreanName()),
                            tuple(US.getCode(), US.getKoreanName()));
            softly.assertThat(actual.usedLanguages())
                    .extracting("code", "name", "level")
                    .containsExactlyInAnyOrder(
                            tuple(KOREAN.getCode(), KOREAN.getName(), 5),
                            tuple(ENGLISH.getCode(), ENGLISH.getName(), 3));
            softly.assertThat(actual.preferredInterests()).containsExactlyInAnyOrder(
                    CategoryInterestsMapDto.builder()
                            .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                            .interests(List.of(
                                    CodeNameDto.of(JAPANESE_FOOD.getName(), JAPANESE_FOOD.getKoreanName()),
                                    CodeNameDto.of(CHINESE_FOOD.getName(), CHINESE_FOOD.getKoreanName())))
                            .build(),
                    CategoryInterestsMapDto.builder()
                            .category(CodeNameDto.of(GAME.getName(), GAME.getKoreanName()))
                            .interests(List.of(
                                    CodeNameDto.of(RPG_GAME.getName(), RPG_GAME.getKoreanName()),
                                    CodeNameDto.of(FPS_GAME.getName(), FPS_GAME.getKoreanName())))
                            .build());
        });
    }

    @DisplayName("UUID로 조회한 유저의 자기 소개와 프로필 이미지 주소를 변경한다.")
    @Test
    void updateProfile() {
        // given
        memberRepository.save(user());

        MemberUpdateProfileRequest request = MemberUpdateProfileRequest.builder()
                .description("Hi")
                .profileImageUri("/123/abc.png")
                .build();

        // when
        memberManager.updateProfile(USER_UUID, request);

        // then
        Member actual = memberRepository.getByUuid(USER_UUID);
        assertThat(actual.getDescription()).isEqualTo("Hi");
        assertThat(actual.getProfileImageUri()).isEqualTo("/123/abc.png");
    }

    @DisplayName("UUID로 조회한 유저의 선호 내용을 수정한다.")
    @Test
    void updatePreference() {
        // given
        memberRepository.save(user());

        MemberUpdatePreferenceRequest request = MemberUpdatePreferenceRequest.builder()
                .preferredCountries(List.of(KOREA.getCode(), JAPAN.getCode()))
                .preferredInterests(List.of(KOREAN_FOOD.getName(), CHINESE_FOOD.getName(), RPG_GAME.getName(), SPORTS_GAME.getName()))
                .build();

        // when
        memberManager.updatePreference(USER_UUID, request);

        // then
        Member member = memberRepository.getByUuid(USER_UUID);
        List<PreferredCountry> preferredCountries = preferredCountryRepository.findAllByMember(member);
        List<PreferredInterests> preferredInterests = preferredInterestsRepository.findAllByMember(member);
        assertSoftly(softly -> {
            softly.assertThat(preferredCountries).map(PreferredCountry::getCode).containsExactlyInAnyOrder(
                    KOREA.getCode(), JAPAN.getCode());
            softly.assertThat(preferredInterests).extracting(PreferredInterests::getInterestsName, PreferredInterests::getInterestsCategory).containsExactlyInAnyOrder(
                    tuple(KOREAN_FOOD.getName(), FOOD.getName()),
                    tuple(CHINESE_FOOD.getName(), FOOD.getName()),
                    tuple(RPG_GAME.getName(), GAME.getName()),
                    tuple(SPORTS_GAME.getName(), GAME.getName()));
        });
    }

}
