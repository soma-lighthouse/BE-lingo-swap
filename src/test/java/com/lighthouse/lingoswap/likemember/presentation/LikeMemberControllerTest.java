package com.lighthouse.lingoswap.likemember.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.question.exception.DuplicateLikeMemberException;
import com.lighthouse.lingoswap.question.exception.LikeMemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeMemberControllerTest extends ControllerTestSupport {

    @DisplayName("질문에 좋아요를 누르면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void postLike() throws Exception {
        // given
        willDoNothing()
                .given(likeMemberManager)
                .createLikeMember(anyString(), anyLong());

        // when & then
        mockMvc.perform(
                        post("/api/v1/{questionId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("이미 좋아요를 눌렀던 질문에 좋아요를 누르면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void postLikeAlreadyLiked() throws Exception {
        // given
        willThrow(new DuplicateLikeMemberException())
                .given(likeMemberManager)
                .createLikeMember(anyString(), anyLong());

        // when & then
        mockMvc.perform(
                        post("/api/v1/{questionId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("좋아요를 취소하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void deleteLike() throws Exception {
        // given
        willDoNothing()
                .given(likeMemberManager)
                .deleteLikeMember(anyString(), anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/v1/{questionId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("누르지 않은 좋아요를 취소하면 상태 코드 400을 반환한다.")
    @WithAuthorizedUser
    @Test
    void deleteLikeNotLiked() throws Exception {
        // given
        willThrow(new LikeMemberNotFoundException())
                .given(likeMemberManager)
                .deleteLikeMember(anyString(), anyLong());

        // when & then
        mockMvc.perform(
                        delete("/api/v1/{questionId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
