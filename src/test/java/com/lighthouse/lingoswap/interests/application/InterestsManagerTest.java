package com.lighthouse.lingoswap.interests.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.interests.domain.repository.InterestsRepository;
import com.lighthouse.lingoswap.interests.dto.InterestsFormResponse;
import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

import static com.lighthouse.lingoswap.common.fixture.CategoryType.FOOD;
import static com.lighthouse.lingoswap.common.fixture.CategoryType.GAME;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static org.assertj.core.api.Assertions.assertThat;

class InterestsManagerTest extends IntegrationTestSupport {

    @Autowired
    InterestsManager interestsManager;

    @Autowired
    InterestsRepository interestsRepository;

    @DisplayName("언어를 한국어로 설정 시 한글로 관심분야 폼을 출력한다.")
    @Test
    void readForm() {
        // given
        LocaleContextHolder.setLocale(Locale.KOREAN);

        // when
        InterestsFormResponse actual = interestsManager.readForm();

        // then
        assertThat(actual.categoryInterestsMapDtos())
                .containsExactlyInAnyOrder(
                        CategoryInterestsMapDto.builder()
                                .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                                .interests(List.of(
                                        CodeNameDto.of(JAPANESE_FOOD.getName(), JAPANESE_FOOD.getKoreanName()),
                                        CodeNameDto.of(CHINESE_FOOD.getName(), CHINESE_FOOD.getKoreanName()),
                                        CodeNameDto.of(KOREAN_FOOD.getName(), KOREAN_FOOD.getKoreanName())
                                ))
                                .build(),
                        CategoryInterestsMapDto.builder()
                                .category(CodeNameDto.of(GAME.getName(), GAME.getKoreanName()))
                                .interests(List.of(
                                        CodeNameDto.of(RPG_GAME.getName(), RPG_GAME.getKoreanName()),
                                        CodeNameDto.of(FPS_GAME.getName(), FPS_GAME.getKoreanName()),
                                        CodeNameDto.of(SPORTS_GAME.getName(), SPORTS_GAME.getKoreanName())
                                ))
                                .build()
                );
    }

}
