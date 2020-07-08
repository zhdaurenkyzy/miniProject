package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserService extends CRUDService<User, Long> {
    User register(User user);
    User findByUserName(String userName);
    Role findRoleByUserName(String userName);
}
