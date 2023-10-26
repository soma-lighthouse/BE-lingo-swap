package com.lighthouse.lingoswap.language.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.language.dto.LanguageFormResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static com.lighthouse.lingoswap.common.fixture.LanguageType.ENGLISH;
import static com.lighthouse.lingoswap.common.fixture.LanguageType.KOREAN;
import static org.assertj.core.api.Assertions.assertThat;

class LanguageManagerTest extends IntegrationTestSupport {

    @Autowired
    LanguageManager languageManager;

    @DisplayName("언어 폼을 조회한다.")
    @Test
    void readForm() {
        // given
        LocaleContextHolder.setLocale(Locale.KOREAN);

        // when
        LanguageFormResponse actual = languageManager.readForm();

        // then
        assertThat(actual.languageForm())
                .containsExactlyInAnyOrder(
                        CodeNameDto.of(KOREAN.getCode(), KOREAN.getName()),
                        CodeNameDto.of(ENGLISH.getCode(), ENGLISH.getName())
                );
    }

}
