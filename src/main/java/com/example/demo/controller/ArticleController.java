package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.mapper.ArticleMapper;
import com.example.demo.model.payload.ArticleRequest;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserService;
import com.example.demo.util.SetterFieldsUtil;
import com.example.demo.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/articles/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    private UserService userService;
    private ArticleService articleService;


    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ArticleDto> createArticle(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ArticleRequest articleRequest) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = new Article();
        SetterFieldsUtil.setFieldsArticle(article, articleRequest);
        article.setUser(user);
        articleService.save(article);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = articleService.getById(id);
        if (article.getUser() == user) {
            articleService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not author", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Long id, @Valid @RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = articleService.getById(id);
        if (article.getUser() == user) {
            SetterFieldsUtil.setFieldsArticle(article, articleRequest);
            articleService.save(article);
            ArticleDto articleDto = ArticleMapper.INSTANCE.toDto(articleService.getById(id));
            return new ResponseEntity<>(articleDto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleDto>> getArticlesByUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        List<Article> articles = articleService.findAllByUser(user);
        List<ArticleDto> articleDTOS = articles.stream().map(i -> ArticleMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(articleDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleDto>> getArticles() {
        List<Article> articles = articleService.getAll();
        List<ArticleDto> articleDTOS = articles.stream().map(i -> ArticleMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(articleDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable(name = "id") Long articleId) {
        Article article = articleService.getById(articleId);
        ValidationUtil.isNotFound(article==null);
        ArticleDto articleDto = ArticleMapper.INSTANCE.toDto(article);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }


}
