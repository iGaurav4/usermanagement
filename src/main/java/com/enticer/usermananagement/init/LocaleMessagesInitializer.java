package com.enticer.usermananagement.init;

import com.enticer.usermananagement.model.LocaleMessages;
import com.enticer.usermananagement.repository.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Order(4)
@Slf4j
@Component
public class LocaleMessagesInitializer implements ApplicationRunner {

    @Value("${usermanagement.lang.message-files}")
    private String[] message_files;

    @Autowired
    private LanguageRepository languageRepository;

    private void saveLocalizationMessagesFromFileToDb(String locale, InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String data = null;
        String messageValue = null;
        String messageKey = null;
        if (locale != null) {
            while ((data = bufferedReader.readLine()) != null) {
                messageValue = data.substring(data.indexOf("=") + 1);
                messageKey = data.substring(0, data.indexOf("="));
                if (messageKey.contains(" ")) {
                    messageKey = messageKey.replaceAll(" ", "");
                }
                languageRepository.save(LocaleMessages.builder().languageLocale(locale).messageKey(messageKey)
                        .messageValue(messageValue).build());
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("Starting Initializer");

        if (languageRepository.count() == 0) {
            log.debug("No localization messages found in the database, creating entries from *.messages files");

            ResourceLoader resourceLoader = new DefaultResourceLoader();

            for (String message_file_name : message_files) {
                String localeString = "en";
                if (message_file_name.contains("messages_")) {
//                    pathString = pathString.substring(pathString.indexOf("s_") + 1);
                    localeString = message_file_name.substring(message_file_name.indexOf("messages_") + 9, message_file_name.indexOf("."));
                }
                log.debug("Saving localisations from {}, with locale {}", message_file_name, localeString);


                Resource resource = resourceLoader.getResource("classpath:" + message_file_name);

                saveLocalizationMessagesFromFileToDb(localeString, resource.getInputStream());
            }
        } else {
            log.debug("localization messages were already initialized");
        }

    }

}