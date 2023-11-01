package com.lighthouse.lingoswap.question.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.member.exception.MemberNotFoundException;
import com.lighthouse.lingoswap.question.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends ControllerTestSupport {

    @DisplayName("카테고리로 질문을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getQuestions() throws Exception {
        // given
        QuestionListResponse response = QuestionListResponse.of(
                1L,
                List.of(QuestionDetail.builder()
                        .questionId(1L)
                        .contents("")
                        .likes(5L)
                        .uuid(USER_UUID)
                        .name(USER_NAME)
                        .region(USER_REGION)
                        .profileImageUri(USER_PROFILE_IMAGE_URI)
                        .liked(true)
                        .build()));
        given(questionManager.read(anyString(), anyLong(), anyLong(), anyInt())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/question")
                                .param("categoryId", "1")
                                .param("next", "1")
                                .param("pageSize", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("질문을 성공적으로 등록하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void postQuestion() throws Exception {
        // given
        willDoNothing()
                .given(questionManager)
                .create(any(QuestionCreateRequest.class));

        QuestionCreateRequest request = QuestionCreateRequest.builder()
                .uuid(USER_UUID)
                .categoryId(1L)
                .content("Hi")
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("잘못된 형식으로 질문을 등록하면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void postQuestionWithBadRequest() throws Exception {
        // given
        QuestionCreateRequest request = QuestionCreateRequest.builder()
                .uuid(USER_UUID)
                .categoryId(null)
                .content("")
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("추천된 질문을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getRecommendation() throws Exception {
        // given
        QuestionRecommendationListResponse response = QuestionRecommendationListResponse.of(
                1L,
                List.of("A", "B"));
        given(questionManager.readRecommendation(anyLong(), anyLong(), anyInt())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/question/recommendation")
                                .param("categoryId", "1")
                                .param("next", "1")
                                .param("pageSize", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("UUID로 유저가 작성한 질문을 조회하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getMyQuestion() throws Exception {
        // given
        MyQuestionsResponse response = MyQuestionsResponse.from(
                List.of(MyQuestionDetail.builder()
                        .questionId(1L)
                        .contents("A")
                        .likes(1L)
                        .category(1L)
                        .createdAt(LocalDateTime.of(2023, 10, 20, 10, 46))
                        .build()));
        given(questionManager.readByCreatedMember(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/user/{uuid}/question", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("존재하지 않는 유저의 UUID로 작성한 질문을 조회하면 상태 코드 404를 반환한다.")
    @WithAuthorizedUser
    @Test
    void getMyQuestionWithNotExistedMemberUuid() throws Exception {
        // given
        willThrow(new MemberNotFoundException())
                .given(questionManager)
                .readByCreatedMember(anyString());

        // when & then
        mockMvc.perform(
                        get("/api/v1/member/{uuid}/question", USER_UUID)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
