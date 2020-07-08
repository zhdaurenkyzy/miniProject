package com.example.demo.model.dto;

import com.example.demo.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private Role role;
    private List<ArticleDto> articles;
    private List<CommentDto> comments;

}
