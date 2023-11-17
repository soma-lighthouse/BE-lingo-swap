package com.lighthouse.lingoswap.common.util;

import com.lighthouse.lingoswap.common.fixture.MemberFixture;
import org.springframework.stereotype.Component;

@Component
public class StubUuidHolder implements UuidHolder {

    @Override
    public String randomUuid() {
        return MemberFixture.USER_UUID;
    }

}
