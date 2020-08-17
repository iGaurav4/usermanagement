package com.enticer.usermananagement;

import com.enticer.usermananagement.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestNGWithSpringApplication.class)
public class TestUserValidator extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private Validator validator;

    @Test(description = "Test to validate User input failure")
    public void testUserValidatorNeg() {

        try {
            User user = new User();
            user.setUsername("ABCdef123");
            user.setPassword("Pass123 ");

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            System.out.println(violations.size());
            violations.stream().forEach(z -> System.out.println(z.getMessage()));
            Assert.assertEquals(violations.size(), 1);
        } catch (Exception e) {
            Assert.fail("Validation threw exception", e);
        } finally {
            log.debug("testUserValidator executed");
        }
    }

    @Test(description = "Test to validate User input success")
    public void testUserValidatorPos() {

        try {
            User user = new User();
            user.setUsername("ABCdef123");
            user.setPassword("Pass123#");

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            System.out.println(violations.size());
            violations.stream().forEach(z -> System.out.println(z.getMessage()));
            Assert.assertTrue(violations.isEmpty());
        } catch (Exception e) {
            Assert.fail("Validation threw exception", e);
        } finally {
            log.debug("testUserValidator executed");
        }
    }
}
