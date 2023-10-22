package com.lighthouse.lingoswap.common.fixture;

public enum LanguageType {

    KOREAN("ko", "한국어"),
    ENGLISH("en", "English");

    private final String code;
    private final String name;

    LanguageType(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
