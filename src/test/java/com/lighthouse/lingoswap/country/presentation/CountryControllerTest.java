package com.lighthouse.lingoswap.country.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.country.dto.CountryFormResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CountryType.KOREA;
import static com.lighthouse.lingoswap.common.fixture.CountryType.US;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CountryControllerTest extends ControllerTestSupport {

    @DisplayName("국가 폼을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getForm() throws Exception {
        // given
        CountryFormResponse response = CountryFormResponse.from(List.of(
                CodeNameDto.of(KOREA.getCode(), KOREA.getKoreanName()),
                CodeNameDto.of(US.getCode(), US.getKoreanName()))
        );
        given(countryManager.readForm()).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/form/country")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

}
