package com.lighthouse.lingoswap.common.support;

import com.lighthouse.lingoswap.chat.service.SendbirdService;
import com.lighthouse.lingoswap.infra.service.CloudFrontService;
import com.lighthouse.lingoswap.infra.service.S3Service;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected SendbirdService sendbirdService;

    @MockBean
    protected S3Service s3Service;

    @MockBean
    protected CloudFrontService cloudFrontService;

}
