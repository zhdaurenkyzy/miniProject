package com.example.demo.util;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.model.payload.ArticleRequest;
import com.example.demo.model.payload.RegisterOrUpdateRequest;

public class SetterFieldsUtil {

    public static void setFieldsArticle(Article article, ArticleRequest articleRequest) {
        article.setText(articleRequest.getText());
        article.setTitle(articleRequest.getTitle());
    }

    public static void setFieldsUser(User user, RegisterOrUpdateRequest request) {
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
    }
}
