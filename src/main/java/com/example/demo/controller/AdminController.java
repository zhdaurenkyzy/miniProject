package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.dto.AdminUserDto;
import com.example.demo.model.mapper.AdminUserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/admin/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdminUserDto resultAdminUserDto = AdminUserMapper.INSTANCE.toDto(user);
        return new ResponseEntity<>(resultAdminUserDto, HttpStatus.OK);
    }

    @GetMapping(value = "users/")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        List<User> users = userService.getAll();
        List<AdminUserDto> resultUsers = users.stream().map(i -> AdminUserMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(resultUsers, HttpStatus.OK);
    }
}
