package com.example.demo.model.payload;

import lombok.Data;

@Data
public class RegisterOrUpdateRequest {

    private String userName;
    private String email;
    private String password;
}
