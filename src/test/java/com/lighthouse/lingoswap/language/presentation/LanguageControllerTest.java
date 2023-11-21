package com.lighthouse.lingoswap.language.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.language.dto.LanguageFormResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.LanguageType.ENGLISH;
import static com.lighthouse.lingoswap.common.fixture.LanguageType.KOREAN;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LanguageControllerTest extends ControllerTestSupport {

    @DisplayName("언어 폼을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getForm() throws Exception {
        // given
        LanguageFormResponse response = LanguageFormResponse.from(List.of(
                CodeNameDto.of(KOREAN.getCode(), KOREAN.getName()),
                CodeNameDto.of(ENGLISH.getCode(), ENGLISH.getName())));
        given(languageManager.readForm()).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/form/language")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

}
