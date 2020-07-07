package com.example.demo.model.dto;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String userName;
    private String email;
    private Role role;

//    public User toUser(){
//        User user = new User();
//        user.setId(id);
//        user.setUserName(userName);
//        user.setEmail(email);
//        user.setRole(role);
//        return user;
//    }
//
//    public static AdminUserDto toDto(User user){
//        AdminUserDto adminUserDto = new AdminUserDto();
//        adminUserDto.setId(user.getId());
//        adminUserDto.setUserName(user.getUserName());
//        adminUserDto.setEmail(user.getEmail());
//        adminUserDto.setRole(user.getRole());
//        return adminUserDto;
//    }
}
