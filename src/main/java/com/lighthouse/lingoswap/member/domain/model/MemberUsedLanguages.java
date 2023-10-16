package com.lighthouse.lingoswap.member.domain.model;

import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
class MemberUsedLanguages {

    @OneToMany(mappedBy = "member")
    private final List<UsedLanguage> usedLanguages = new ArrayList<>();

    public List<UsedLanguage> getUsedLanguages() {
        return usedLanguages;
    }

}
