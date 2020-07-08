package com.example.demo.model.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {

    @NotEmpty
    private String commentText;
}
