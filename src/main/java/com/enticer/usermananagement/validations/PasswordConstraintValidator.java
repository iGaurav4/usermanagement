package com.enticer.usermananagement.validations;

import com.enticer.usermananagement.annotation.ValidPassword;
import com.enticer.usermananagement.util.DBMessageSource;
import org.passay.*;
import org.passay.spring.SpringMessageResolver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    DBMessageSource messageSource;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (messageSource == null) {
          throw new IllegalStateException("DBMessageSource has not been wired");
        }
        MessageResolver messageResolver = new SpringMessageResolver(messageSource);
        PasswordValidator validator = new PasswordValidator(messageResolver, Arrays.asList(
                // at least 6 characters
                new LengthRule(6, 32),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                //no whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
//		String localMessage = messageSource.getMessage(message);
        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

}
