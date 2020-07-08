package com.example.demo.model.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ArticleRequest {
    private String title;
    private String text;

}
