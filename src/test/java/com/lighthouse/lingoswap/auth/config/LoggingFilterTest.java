package com.lighthouse.lingoswap.auth.config;

import com.lighthouse.lingoswap.ControllerTestSupport;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class LoggingFilterTest extends ControllerTestSupport {

    // user-agent:"PostmanRuntime/7.35.0", accept:"*/*", host:"localhost:8080", accept-encoding:"gzip, deflate, br", connection:"keep-alive"
    @Test
    void test() throws Exception {
        mockMvc.perform(
                get("/api/v1/question")
                        .queryParam("categoryId", "1")
                        .header("user-agent", "PostmanRuntime/7.35.0")
                        .header("host", "PostmanRuntime/7.35.0")
        );
    }

}
