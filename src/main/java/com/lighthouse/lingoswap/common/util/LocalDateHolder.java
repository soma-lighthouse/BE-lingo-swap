package com.lighthouse.lingoswap.common.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Profile({"local", "dev"})
@Component
public class LocalDateHolder implements DateHolder {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }

}
