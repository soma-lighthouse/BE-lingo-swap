package com.lighthouse.lingoswap.common.util;

import java.time.LocalDate;

public interface TimeHolder {

    LocalDate now();

    long currentTimeMillis();

}
