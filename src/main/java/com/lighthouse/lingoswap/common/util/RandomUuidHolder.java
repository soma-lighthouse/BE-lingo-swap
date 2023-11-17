package com.lighthouse.lingoswap.common.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("!test")
@Service
public class RandomUuidHolder implements UuidHolder {

    @Override
    public String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
