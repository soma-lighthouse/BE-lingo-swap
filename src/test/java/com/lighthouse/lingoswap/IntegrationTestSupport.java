package com.lighthouse.lingoswap;

import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.common.util.DateHolder;
import com.lighthouse.lingoswap.infra.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @Autowired
    protected DateHolder dateHolder;

    @MockBean
    protected SendbirdService sendbirdService;

    @MockBean
    protected S3Service s3Service;

}
