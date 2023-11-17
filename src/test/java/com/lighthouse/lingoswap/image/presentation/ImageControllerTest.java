package com.lighthouse.lingoswap.image.presentation;

import com.lighthouse.lingoswap.ControllerTestSupport;
import com.lighthouse.lingoswap.common.security.WithAuthorizedUser;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlRequest;
import com.lighthouse.lingoswap.member.dto.MemberPreSignedUrlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.net.URL;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImageControllerTest extends ControllerTestSupport {

    private static final String IMAGE_KEY = "/2c1a2c3d-4d8b-46f4-9011-189cd1fc8644/1.jpg";
    private static final String PRE_SIGNED_URL = "https://abc/profiles/2c1a2c3d-4d8b-46f4-9011-189cd1fc8644/1.jpg";

    @DisplayName("S3 pre-signed url을 발급하면 상태 코드 200을 반환한다.")
    @WithAuthorizedUser
    @Test
    void getPreSignedUrl() throws Exception {
        // given
        MemberPreSignedUrlRequest request = MemberPreSignedUrlRequest.from(IMAGE_KEY);
        MemberPreSignedUrlResponse response = MemberPreSignedUrlResponse.from(new URL(PRE_SIGNED_URL));
        given(imageManager.createPreSignedUrl(request)).willReturn(response);

        // when & then
        mockMvc.perform(
                        post("/api/v1/admin/upload/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20000"))
                .andExpect(jsonPath("$.message").value("Request sent successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

}
