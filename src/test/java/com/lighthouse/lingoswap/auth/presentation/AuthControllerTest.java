package com.lighthouse.lingoswap.auth.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.auth.dto.LoginResponse;
import com.lighthouse.lingoswap.auth.dto.MemberCreateRequest;
import com.lighthouse.lingoswap.auth.dto.ReissueRequest;
import com.lighthouse.lingoswap.auth.dto.TokenPairInfoResponse;
import com.lighthouse.lingoswap.auth.exception.InvalidIdTokenException;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.CountryType.KOREA;
import static com.lighthouse.lingoswap.common.fixture.CountryType.US;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.CHINESE_FOOD;
import static com.lighthouse.lingoswap.common.fixture.InterestsType.KOREAN_FOOD;
import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static com.lighthouse.lingoswap.common.fixture.TokenPairFixture.ACCESS_TOKEN;
import static com.lighthouse.lingoswap.common.fixture.TokenPairFixture.REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName("로그인에 성공하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void login() throws Exception {
        // given
        LoginResponse response = buildLoginResponse();
        given(authManager.login(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    private LoginResponse buildLoginResponse() {
        return LoginResponse.builder()
                .uuid(USER_UUID)
                .username(USER_USERNAME)
                .tokens(buildTokenPairInfo())
                .build();
    }

    private TokenPairInfoResponse buildTokenPairInfo() {
        return TokenPairInfoResponse.builder()
                .accessToken(ACCESS_TOKEN)
                .expiresIn(1L)
                .refreshToken(REFRESH_TOKEN)
                .refreshTokenExpiresIn(1L)
                .build();
    }

    @DisplayName("잘못된 id token으로 로그인하면 상태 코드 403을 반환한다.")
    @WithAuthorizedUser
    @Test
    void loginWithBadIdToken() throws Exception {
        // given
        willThrow(InvalidIdTokenException.class)
                .given(authManager)
                .login(anyString());

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/login"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("40300"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.message").value("Unauthorized access."));
    }

    @DisplayName("존재하지 않는 회원의 id token으로 로그인하면 상태 코드 404를 반환한다.")
    @WithAuthorizedUser
    @Test
    void loginWithNotExistedMemberIdToken() throws Exception {
        // given
        willThrow(MemberNotFoundException.class)
                .given(authManager)
                .login(anyString());

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/login"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("40401"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.message").isEmpty());
    }

    @DisplayName("회원 가입에 성공하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void signup() throws Exception {
        // given
        LoginResponse response = buildLoginResponse();
        given(authManager.signup(any(MemberCreateRequest.class))).willReturn(response);

        MemberCreateRequest request = buildMemberCreateRequest();

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    private MemberCreateRequest buildMemberCreateRequest() {
        return MemberCreateRequest.builder()
                .birthday(USER_BIRTHDAY)
                .name(USER_NAME)
                .email(USER_USERNAME)
                .gender(USER_GENDER)
                .description(USER_DESCRIPTION)
                .region(USER_REGION)
                .preferredCountries(List.of(KOREA.getCode(), US.getCode()))
                .preferredInterests(List.of(KOREAN_FOOD.getName(), CHINESE_FOOD.getName()))
                .build();
    }

    @DisplayName("잘못된 id token으로 회원 가입하면 상태 코드 403을 반환한다.")
    @WithAuthorizedUser
    @Test
    void signupWithBadIdToken() throws Exception {
        // given
        MemberCreateRequest request = buildMemberCreateRequest();

        willThrow(InvalidIdTokenException.class)
                .given(authManager)
                .signup(request);

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("40300"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.message").value("Unauthorized access."));
    }

    @DisplayName("잘못된 형식으로 회원 가입하면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void signupWithBadRequest() throws Exception {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .birthday(USER_BIRTHDAY)
                .name(null)
                .email(USER_USERNAME)
                .gender(USER_GENDER)
                .description(USER_DESCRIPTION)
                .region(USER_REGION)
                .preferredCountries(List.of(KOREA.getCode(), US.getCode()))
                .preferredInterests(List.of(KOREAN_FOOD.getName(), CHINESE_FOOD.getName()))
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("refresh token으로 재발급하면 상태 코드 200을 반환한다.")
    @Test
    void reissue() throws Exception {
        // given
        TokenPairInfoResponse response = buildTokenPairInfo();
        given(authManager.reissue(any(ReissueRequest.class))).willReturn(response);

        ReissueRequest request = ReissueRequest.from(REFRESH_TOKEN);

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("잘못된 refresh token으로 재발급하면 상태 코드 403을 반환한다.")
    @Test
    void reissueWithBadRefreshToken() throws Exception {
        // given
        ReissueRequest request = ReissueRequest.from(REFRESH_TOKEN);

        willThrow(InvalidIdTokenException.class)
                .given(authManager)
                .reissue(request);

        // when & then
        mockMvc.perform(
                        post("/api/v1/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("40300"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.message").value("Unauthorized access."));
    }

    @DisplayName("회원을 삭제하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void deleteAuth() throws Exception {
        // given
        doNothing().when(authManager).delete(anyString());

        // when & then
        mockMvc.perform(
                        delete("/api/v1/auth"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
