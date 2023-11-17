package com.lighthouse.lingoswap.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StubTimeHolder implements TimeHolder {

    @Override
    public LocalDate now() {
        return LocalDate.of(2023, 10, 20);
    }

    @Override
    public long currentTimeMillis() {
        return 1_698_314_938;
    }

}
