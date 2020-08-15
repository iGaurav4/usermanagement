package com.enticer.usermananagement.exception;

import com.enticer.usermananagement.util.MessageByLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private MessageByLocale messageByLocale;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException exception) {
        HashMap<String, String> rtn = new HashMap<>();
        exception.getConstraintViolations().forEach((constraintViolation -> {
            String message = constraintViolation.getMessage();
            String localeMessage = messageByLocale.getMessage(message);
            if (constraintViolation.getPropertyPath().toString().equals("")) {
                if (!rtn.containsKey("general")) {
                    rtn.put("general", localeMessage);
                } else {
                    rtn.put("general", rtn.get("general").concat(localeMessage));
                }
            } else {
                rtn.put(constraintViolation.getPropertyPath().toString(), localeMessage);
            }
        }));

        return new ResponseEntity<>(rtn, HttpStatus.BAD_REQUEST);
    }

}
