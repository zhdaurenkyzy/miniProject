package com.example.demo.security;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.security.jwt.JWTUser;
import com.example.demo.security.jwt.JWTUserFactory;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JWTUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JWTUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("User with userName: " + username + "not found");
        }
        JWTUser jwtUser = JWTUserFactory.create(user);
        log.info("in loadUserByUserName - user by username: " + username + "is loaded.");
        return jwtUser;
    }
}
