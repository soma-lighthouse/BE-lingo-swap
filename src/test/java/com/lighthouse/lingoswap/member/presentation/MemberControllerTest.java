package com.lighthouse.lingoswap.member.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.member.dto.*;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CategoryType.FOOD;
import static com.lighthouse.lingoswap.common.fixture.CountryType.JAPAN;
import static com.lighthouse.lingoswap.common.fixture.CountryType.KOREA;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.*;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerTest extends ControllerTestSupport {

    @DisplayName("유저의 프로필을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getProfile() throws Exception {
        // given
        MemberProfileResponse response = MemberProfileResponse.builder()
                .uuid(USER_UUID)
                .profileImageUri(USER_PROFILE_IMAGE_URI)
                .name(USER_NAME)
                .description(USER_DESCRIPTION)
                .region(CodeNameDto.of(KOREA.getCode(), KOREA.getKoreanName()))
                .preferredCountries(
                        List.of(CodeNameDto.of(KOREA.getCode(), KOREA.getKoreanName())))
                .preferredInterests(
                        List.of(CategoryInterestsMapDto.builder()
                                .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                                .interests(List.of(
                                        CodeNameDto.of(JAPANESE_FOOD.getName(), JAPANESE_FOOD.getKoreanName()),
                                        CodeNameDto.of(CHINESE_FOOD.getName(), CHINESE_FOOD.getKoreanName())))
                                .build()))
                .build();
        given(memberManager.readProfile(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/{uuid}/profile", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("존재하지 않는 유저의 UUID로 프로필을 조회하면 상태 코드 404를 반환한다.")
    @WithAuthorizedUser
    @Test
    void getProfileWithNotExistedMemberUuid() throws Exception {
        // given
        given(memberManager.readProfile(anyString())).willThrow(new MemberNotFoundException());

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/{uuid}/profile", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("40401"))
                .andExpect(jsonPath("$.message").value("Couldn't find user."))
                .andExpect(jsonPath("$.data.message").isEmpty());
    }

    @DisplayName("유저의 선호 내용을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getPreference() throws Exception {
        // given
        MemberPreferenceResponse response = MemberPreferenceResponse.builder()
                .preferredCountries(List.of(CodeNameDto.of(KOREA.getCode(), KOREA.getKoreanName())))
                .preferredInterests(List.of(CategoryInterestsMapDto.builder()
                        .category(CodeNameDto.of(FOOD.getName(), FOOD.getKoreanName()))
                        .interests(List.of(
                                CodeNameDto.of(JAPANESE_FOOD.getName(), JAPANESE_FOOD.getKoreanName()),
                                CodeNameDto.of(CHINESE_FOOD.getName(), CHINESE_FOOD.getKoreanName())))
                        .build()))
                .build();
        given(memberManager.readPreference(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/{uuid}/preference", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("존재하지 않는 유저의 UUID로 선호 내용을 조회하면 상태 코드 404를 반환한다.")
    @WithAuthorizedUser
    @Test
    void getPreferenceWithNotExistedMemberUuid() throws Exception {
        // given
        given(memberManager.readPreference(anyString())).willThrow(new MemberNotFoundException());

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/{uuid}/preference", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("40401"))
                .andExpect(jsonPath("$.message").value("Couldn't find user."))
                .andExpect(jsonPath("$.data.message").isEmpty());
    }

    @DisplayName("유저의 프로필을 성공적으로 수정하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void patchProfile() throws Exception {
        // given
        willDoNothing()
                .given(memberManager)
                .updateProfile(anyString(), any(MemberUpdateProfileRequest.class));

        MemberUpdateProfileRequest request = MemberUpdateProfileRequest.builder()
                .description(USER_DESCRIPTION)
                .profileImageUri(USER_PROFILE_IMAGE_URI)
                .build();

        // when & then
        mockMvc.perform(
                        patch("/api/v1/user/{uuid}/profile", USER_UUID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("잘못된 형식으로 유저의 프로필을 수정하면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void patchProfileWithBadRequest() throws Exception {
        // given
        willDoNothing()
                .given(memberManager)
                .updateProfile(anyString(), any(MemberUpdateProfileRequest.class));

        MemberUpdateProfileRequest request = MemberUpdateProfileRequest.builder()
                .description(null)
                .profileImageUri(null)
                .build();

        // when & then
        mockMvc.perform(
                        patch("/api/v1/user/{uuid}/profile", USER_UUID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("유저의 선호 분야를 성공적으로 수정하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void patchPreference() throws Exception {
        // given
        willDoNothing()
                .given(memberManager)
                .updatePreference(anyString(), any(MemberUpdatePreferenceRequest.class));

        MemberUpdatePreferenceRequest request = MemberUpdatePreferenceRequest.builder()
                .preferredCountries(List.of(KOREA.getCode(), JAPAN.getCode()))
                .preferredInterests(List.of(KOREAN_FOOD.getName(), CHINESE_FOOD.getName(), RPG_GAME.getName(), SPORTS_GAME.getName()))
                .build();

        // when & then
        mockMvc.perform(
                        patch("/api/v1/user/{uuid}/preference", USER_UUID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("잘못된 형식으로 유저의 선호 분야를 수정하면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void patchPreferenceWithBadRequest() throws Exception {
        // given
        willDoNothing()
                .given(memberManager)
                .updatePreference(anyString(), any(MemberUpdatePreferenceRequest.class));

        MemberUpdatePreferenceRequest request = MemberUpdatePreferenceRequest.builder()
                .preferredCountries(null)
                .preferredInterests(null)
                .build();

        // when & then
        mockMvc.perform(
                        patch("/api/v1/user/{uuid}/preference", USER_UUID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
