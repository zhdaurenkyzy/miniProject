package com.example.demo.model.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ArticleRequest {
    @NotEmpty
    @Size(max = 100, message = "Title must contain max of 100 symbols")
    private String title;

    @Size(max = 400, message = "Title must contain max of 100 symbols")
    private String text;

}
