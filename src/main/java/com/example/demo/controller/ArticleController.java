package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.model.dto.AdminUserDto;
import com.example.demo.model.dto.ArticleDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.AdminUserMapper;
import com.example.demo.model.mapper.ArticleMapper;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.payload.ArticleRequest;
import com.example.demo.model.payload.RegisterOrUpdateRequest;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/article/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ArticleDto> createArticle(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ArticleRequest articleRequest) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = new Article();
        article.setText(articleRequest.getText());
        article.setTitle(articleRequest.getTitle());
        article.setUser(user);
        articleService.save(article);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = articleService.getById(id);
        if(article.getUser()==user) {
            articleService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not author", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = articleService.getById(id);
        if (article.getUser()==user) {
            article.setTitle(articleRequest.getTitle());
            article.setText(articleRequest.getText());
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
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable(name = "id") Long articleId){
        Article article = articleService.getById(articleId);
        if(article == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       ArticleDto articleDto= ArticleMapper.INSTANCE.toDto(article);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }


}
