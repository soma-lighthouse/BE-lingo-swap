package com.lighthouse.lingoswap.usedlanguage.domain.model;

import com.lighthouse.lingoswap.language.domain.model.Language;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class LanguageInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    LanguageInfo(final Language language) {
        this.language = language;
    }

    String getCode() {
        return language.getCode();
    }

    String getName() {
        return language.getName();
    }

}
