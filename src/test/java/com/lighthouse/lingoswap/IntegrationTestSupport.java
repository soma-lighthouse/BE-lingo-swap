package com.lighthouse.lingoswap;

import com.lighthouse.lingoswap.chat.service.SendbirdService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected SendbirdService sendbirdService;

}
