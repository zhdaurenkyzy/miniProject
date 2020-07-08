package com.example.demo.service;

import com.example.demo.model.Article;
import com.example.demo.model.User;

import java.util.List;

public interface ArticleService extends CRUDService<Article, Long> {
    List<Article> findAllByUser(User user);
}
