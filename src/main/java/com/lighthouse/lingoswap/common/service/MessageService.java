package com.lighthouse.lingoswap.common.service;

import com.lighthouse.lingoswap.common.dto.CodeNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MessageService {

    private static final String COUNTRY_MESSAGE_CODE_FORMAT = "country.%s";
    private static final String CATEGORY_MESSAGE_CODE_FORMAT = "category.%s";
    private static final String INTERESTS_MESSAGE_CODE_FORMAT = "interests.%s";

    private final MessageSource messageSource;

    public String translate(final String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public CodeNameDto toTranslatedCountryCodeNameDto(final String code) {
        return CodeNameDto.builder()
                .code(code)
                .name(translate(COUNTRY_MESSAGE_CODE_FORMAT.formatted(code)))
                .build();
    }

    public CodeNameDto toTranslatedCategoryCodeNameDto(final String code) {
        return CodeNameDto.builder()
                .code(code)
                .name(translate(CATEGORY_MESSAGE_CODE_FORMAT.formatted(code)))
                .build();
    }

    public CodeNameDto toTranslatedInterestsCodeNameDto(final String code) {
        return CodeNameDto.builder()
                .code(code)
                .name(translate(INTERESTS_MESSAGE_CODE_FORMAT.formatted(code)))
                .build();
    }

}
