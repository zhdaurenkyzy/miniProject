package com.example.demo.model.dto;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {

    private Long id;
    private String commentText;
    private String authorName;
    private Long articleId;
}
