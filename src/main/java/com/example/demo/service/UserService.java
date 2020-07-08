package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface UserService extends CRUDService<User, Long> {
    User register(User user);

    User findByUserName(String userName);

    Role findRoleByUserName(String userName);
}
