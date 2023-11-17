package com.lighthouse.lingoswap.common.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Profile("!test")
@Component
public class LocalTimeHolder implements TimeHolder {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

}
