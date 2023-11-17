package com.lighthouse.lingoswap.interests.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.interests.dto.InterestsFormResponse;
import com.lighthouse.lingoswap.member.dto.CategoryInterestsMapDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CategoryType.FOOD;
import static com.lighthouse.lingoswap.common.fixture.CategoryType.GAME;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InterestsControllerTest extends ControllerTestSupport {

    @DisplayName("관심분야 폼을 조회하면 상태 코드 200을 반환한다.")
    @Test
    void getInterestsForm() throws Exception {
        // given
        InterestsFormResponse response = InterestsFormResponse.from(List.of(
                CategoryInterestsMapDto.builder()
                        .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                        .interests(List.of(
                                CodeNameDto.of("Japanese", "Japanese"),
                                CodeNameDto.of("Chinese", "Chinese"),
                                CodeNameDto.of("Korean", "Korean")
                        ))
                        .build(),
                CategoryInterestsMapDto.builder()
                        .category(CodeNameDto.of(GAME.getName(), GAME.getKoreanName()))
                        .interests(List.of(
                                CodeNameDto.of("RPG", "RPG"),
                                CodeNameDto.of("FPS", "FPS"),
                                CodeNameDto.of("Sports Game", "Sports")
                        ))
                        .build()
        ));
        given(interestsManager.readForm()).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/form/interests")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

}
