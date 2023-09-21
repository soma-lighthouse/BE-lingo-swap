package com.lighthouse.lingoswap.member.service;

import com.lighthouse.lingoswap.member.dto.LanguageFormResponse;
import com.lighthouse.lingoswap.member.entity.Language;
import com.lighthouse.lingoswap.member.repository.LanguageQueryRepository;
import com.lighthouse.lingoswap.member.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageQueryRepository languageQueryRepository;

    public Language findLanguageByCode(String code) {
        return languageRepository.findLanguageByCode(code).orElseThrow(() -> new RuntimeException("언어 코드가 존재하지 않습니다"));
    }

    public LanguageFormResponse getAllLanguages() {
        return new LanguageFormResponse(languageQueryRepository.findAllLanguage());
    }

    public List<Language> findAllByCodes(List<String> codes) {
        return languageRepository.findAllByCodeIn(codes);
    }
}
