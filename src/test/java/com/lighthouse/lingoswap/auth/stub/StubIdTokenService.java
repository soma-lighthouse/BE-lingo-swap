package com.lighthouse.lingoswap.auth.stub;

import com.lighthouse.lingoswap.auth.application.IdTokenService;
import org.springframework.stereotype.Service;

import static com.lighthouse.lingoswap.common.fixture.MemberFixture.USER_USERNAME;

@Service
public class StubIdTokenService implements IdTokenService {

    @Override
    public String parseIdToken(final String idTokenString) {
        return USER_USERNAME;
    }

}
