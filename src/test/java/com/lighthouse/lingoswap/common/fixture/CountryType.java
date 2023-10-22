package com.lighthouse.lingoswap.common.fixture;

public enum CountryType {

    KOREA("kr", "Korea, Republic of", "대한민국"),
    US("us", "United States of America", "미국"),
    JAPAN("jp", "Japan", "일본");

    private final String code;
    private final String englishName;
    private final String koreanName;

    CountryType(final String code, final String englishName, final String koreanName) {
        this.code = code;
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }

}
