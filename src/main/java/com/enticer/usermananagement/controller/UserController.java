package com.enticer.usermananagement.controller;

import com.enticer.usermananagement.model.User;
import com.enticer.usermananagement.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.Set;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @PostMapping("")
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody @Valid User user, BindingResult result){
        if (result.hasErrors()) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = (Validator) validatorFactory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            throw new ConstraintViolationException(violations);
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        userService.saveOrUpdateUser(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
