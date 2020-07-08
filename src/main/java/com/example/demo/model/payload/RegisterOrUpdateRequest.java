package com.example.demo.model.payload;

import com.example.demo.validation.CustomValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegisterOrUpdateRequest {

    @Size(min = 4, message = "UserName must contain min of 4 symbols")
    private String userName;
    @Email
    private String email;

    @CustomValidPassword
    private String password;
}
