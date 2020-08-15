package com.enticer.usermananagement.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


import java.util.Locale;

@Slf4j
@Component
public class MessageByLocale {
    @Autowired
    private DBMessageSource messageSource;

    public String getMessage(String data) {
        Locale locale = LocaleContextHolder.getLocale();
//        log.debug(LocaleContextHolder.getLocale().toString());
        return messageSource.getMessage(data,null, locale);
    }
}



