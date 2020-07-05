package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    Role() {
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
