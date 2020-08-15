package com.enticer.usermananagement.annotation;



import com.enticer.usermananagement.util.MessagesConstant;
import com.enticer.usermananagement.validations.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {

    String message() default MessagesConstant.VALIDPASSWORD;

    Class<?> [] groups() default{};

    Class<? extends Payload> [] payload() default {};

}
