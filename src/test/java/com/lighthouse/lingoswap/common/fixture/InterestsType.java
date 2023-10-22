package com.lighthouse.lingoswap.common.fixture;

public enum InterestsType {

    JAPANESE_FOOD(CategoryType.FOOD, "Japanese", "Japanese", "일식"),
    CHINESE_FOOD(CategoryType.FOOD, "Chinese", "Chinese", "중식"),
    KOREAN_FOOD(CategoryType.FOOD, "Korean", "Korean", "한식"),
    RPG_GAME(CategoryType.GAME, "RPG", "RPG", "RPG"),
    FPS_GAME(CategoryType.GAME, "FPS", "FPS", "FPS"),
    SPORTS_GAME(CategoryType.GAME, "Sports Game", "Sports Game", "스포츠 게임");

    private final CategoryType categoryType;
    private final String name;
    private final String englishName;
    private final String koreanName;

    InterestsType(final CategoryType categoryType, final String name, final String englishName, final String koreanName) {
        this.categoryType = categoryType;
        this.name = name;
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
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
