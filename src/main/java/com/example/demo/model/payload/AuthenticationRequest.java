package com.example.demo.model.payload;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String userName;
    private String password;
}
