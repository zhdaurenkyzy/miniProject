package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.payload.AuthenticationRequest;
import com.example.demo.model.payload.RegisterOrUpdateRequest;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/")
@Slf4j
public class AuthenticationController {


    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationRequest requestDto) {
        try {
            String userName = requestDto.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, requestDto.getPassword()));
            User user = userService.findByUserName(userName);
            if (user == null) {
                throw new UsernameNotFoundException("User with userName: " + userName + " not found");
            }
            String token = jwtTokenProvider.createToken(userName, user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("userName", userName);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid userName or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterOrUpdateRequest registerDto) {
        String userName = registerDto.getUserName();
        if (userService.findByUserName(userName) != null) {
            return new ResponseEntity<>("User with username: " + userName + " already is existed", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        userService.register(user);
        return new ResponseEntity("User " + userName + " has successfully registered", HttpStatus.CREATED);
    }
}
