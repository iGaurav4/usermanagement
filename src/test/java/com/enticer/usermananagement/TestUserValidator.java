package com.enticer.usermananagement;

import com.enticer.usermananagement.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestNGWithSpringApplication.class)
public class TestUserValidator extends AbstractTransactionalTestNGSpringContextTests {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Test(description = "Test to validate User input")
    public void testUserValidator() {

        try {
            User user = new User();
            user.setUsername("ABCdef123");
            user.setPassword("Pass123 ");

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            System.out.println( violations.size());
            violations.stream().forEach(z -> {
                System.out.println(z.getMessage());
            });
            Assert.assertTrue(violations.isEmpty());
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            log.debug("testUserValidator executed");
        }
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }
}
