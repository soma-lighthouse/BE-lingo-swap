package com.lighthouse.lingoswap.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MessageSourceService {

    private final MessageSource messageSource;

    public String translate(final String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
