package com.lighthouse.lingoswap.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StubDateHolder implements DateHolder {

    @Override
    public LocalDate now() {
        return LocalDate.of(2023, 10, 20);
    }

}
