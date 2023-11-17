package com.lighthouse.lingoswap.language.application;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import com.lighthouse.lingoswap.language.domain.model.Language;
import com.lighthouse.lingoswap.language.domain.repository.LanguageRepository;
import com.lighthouse.lingoswap.language.dto.LanguageFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LanguageManager {

    private final LanguageRepository languageRepository;

    public LanguageFormResponse readForm() {
        List<Language> languages = languageRepository.findAll();
        List<CodeNameDto> dto = languages.stream().map(l -> CodeNameDto.of(l.getCode(), l.getName())).toList();
        return LanguageFormResponse.from(dto);
    }

}
