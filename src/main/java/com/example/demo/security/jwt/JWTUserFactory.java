package com.example.demo.security.jwt;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JWTUserFactory {

    public JWTUserFactory() {
    }

    public static JWTUser create(User user){
        return new JWTUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                convertToGrantedAuthority(user.getRole())
        );
    }

    private static List<GrantedAuthority> convertToGrantedAuthority(Role role){
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(role);
        return authList;
    }
}
