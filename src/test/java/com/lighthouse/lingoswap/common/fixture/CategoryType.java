package com.lighthouse.lingoswap.common.fixture;

public enum CategoryType {

    FOOD("Food", "Food", "음식"),
    GAME("Game", "Game", "게임");

    private final String name;
    private final String englishName;
    private final String koreanName;

    CategoryType(final String name, final String englishName, final String koreanName) {
        this.name = name;
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }
    
}
