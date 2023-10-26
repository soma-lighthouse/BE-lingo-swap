package com.lighthouse.lingoswap;

import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.common.util.TimeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected SendbirdService sendbirdService;

    @Autowired
    protected TimeHolder timeHolder;

}
