package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.entity.Language;
import com.lighthouse.lingoswap.member.entity.Member;
import com.lighthouse.lingoswap.member.entity.UsedLanguage;
import com.lighthouse.lingoswap.member.repository.UsedLanguageRepository;
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
