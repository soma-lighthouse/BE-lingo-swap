package com.lighthouse.lingoswap.country.application;

import com.lighthouse.lingoswap.IntegrationTestSupport;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static com.lighthouse.lingoswap.common.fixture.CountryType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class CountryManagerTest extends IntegrationTestSupport {

    @Autowired
    CountryManager countryManager;

    @DisplayName("언어를 한국어로 설정 시 한글로 국가 폼을 출력한다.")
    @Test
    void readKoreanForm() {
        // given
        LocaleContextHolder.setLocale(Locale.KOREAN);

        // when
        CountryFormResponse actual = countryManager.readForm();

        // then
        assertThat(actual.countryForm()).extracting("code", "name")
                .containsExactlyInAnyOrder(
                        tuple(KOREA.getCode(), KOREA.getKoreanName()),
                        tuple(US.getCode(), US.getKoreanName()),
                        tuple(JAPAN.getCode(), JAPAN.getKoreanName())
                );
    }

}
