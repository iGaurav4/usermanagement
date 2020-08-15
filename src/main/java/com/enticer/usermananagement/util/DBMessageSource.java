package com.enticer.usermananagement.util;


import com.enticer.usermananagement.model.LocaleMessages;
import com.enticer.usermananagement.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;


import java.text.MessageFormat;
import java.util.Locale;

@Component
public class DBMessageSource extends AbstractMessageSource {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {
        final String DEFAULT_LOCALE = "en";
        LocaleMessages localeMessages = languageRepository.findByMessageKeyAndLanguageLocale(key, locale.getLanguage());
        if (localeMessages == null){
            localeMessages = languageRepository.findByMessageKeyAndLanguageLocale(key, DEFAULT_LOCALE);
        }
        if(localeMessages == null){
            return null;
        }
        MessageFormat messageFormat = new MessageFormat(localeMessages.getMessageValue() , locale);
        return messageFormat;
    }

}
