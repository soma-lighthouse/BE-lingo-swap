package com.lighthouse.lingoswap.preferredcountry.fixture;

import com.lighthouse.lingoswap.country.domain.model.Country;

public class CountryFixture {

    public static final String KOREA_CODE = "kr";
    public static final String US_CODE = "us";

    public static Country korea() {
        return Country.builder()
                .code(KOREA_CODE)
                .build();
    }

    public static Country us() {
        return Country.builder()
                .code(US_CODE)
                .build();
    }

}
