package com.enticer.usermananagement.model;

import com.enticer.usermananagement.annotation.ValidPassword;
import com.enticer.usermananagement.util.Constant;
import com.enticer.usermananagement.util.MessagesConstant;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 6, max = 32, message = MessagesConstant.SIZE_CUSTOMUSER_USERNAME)
    @Pattern(regexp = Constant.ALPHANUMERIC_PATTERN, message = MessagesConstant.PATTERN_CUSTOMUSER_USERNAME)
    private String username;


    @NotNull(message = MessagesConstant.VALIDATION_NOT_NULL)
    @NotEmpty(message = MessagesConstant.VALIDATION_NOT_EMPTY)
    @ValidPassword(message = MessagesConstant.STRONG_PASSWORD)
    private String password;



}
