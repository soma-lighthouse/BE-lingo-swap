package com.lighthouse.lingoswap.member.application;

import com.lighthouse.lingoswap.member.domain.model.Language;
import com.lighthouse.lingoswap.member.domain.model.Member;
import com.lighthouse.lingoswap.usedlanguage.domain.model.UsedLanguage;
import com.lighthouse.lingoswap.usedlanguage.domain.repository.UsedLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsedLanguageService {

    private final UsedLanguageRepository usedLanguageRepository;


    public void save(UsedLanguage usedLanguage) {
        usedLanguageRepository.save(usedLanguage);
    }

    public List<UsedLanguage> findByMember(Member member) {
        return usedLanguageRepository.findByMember(member);
    }

    public void saveAll(List<UsedLanguage> usedLanguages) {
        usedLanguageRepository.saveAll(usedLanguages);
    }

    public void deleteByLanguageCodeIn(List<Language> languages) {
        usedLanguageRepository.deleteAllByLanguageIn(languages);
    }
}
