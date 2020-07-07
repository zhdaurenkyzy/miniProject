package com.example.demo.model.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {

    private Long articleId;
    private String title;
    private String text;
    private String userName;
    private List<CommentDto> comments;
}
