package com.enticer.usermananagement.repository;


import com.enticer.usermananagement.model.LocaleMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LocaleMessages, Long> {

    LocaleMessages findByMessageKeyAndLanguageLocale(String messageKey, String languageLocale);
}