package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageSource messageSource;

    public String translate(final String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public CodeNameDto toTranslatedCodeNameDto(final String code) {
        return CodeNameDto.builder()
                .code(code)
                .name(translate(code))
                .build();
    }

}
