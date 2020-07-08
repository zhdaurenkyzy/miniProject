package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.mapper.CommentMapper;
import com.example.demo.model.payload.CommentRequest;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
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
@RequestMapping(value = "/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private CommentService commentService;
    private ArticleService articleService;
    private UserService userService;


    @Autowired
    public CommentController(CommentService commentService, UserService userService, ArticleService articleService) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> createComment(@PathVariable("id") Long articleId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentRequest commentRequest) {
        User user = userService.findByUserName(userDetails.getUsername());
        Article article = articleService.getById(articleId);
        if (article != null) {
            Comment comment = new Comment();
            comment.setCommentText(commentRequest.getCommentText());
            comment.setArticle(article);
            comment.setAuthor(user);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Article with id " + articleId + " not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Comment comment = commentService.getById(id);
        if (comment.getAuthor() == user) {
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not author of comment", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<CommentDto> updateComment(@PathVariable("id") Long id, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        Comment comment = commentService.getById(id);
        if (comment.getAuthor() == user) {
            comment.setCommentText(commentRequest.getCommentText());
            commentService.save(comment);
            CommentDto commentDto = CommentMapper.INSTANCE.toDto(commentService.getById(id));
            return new ResponseEntity<>(commentDto, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id) {
        Comment comment = commentService.getById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CommentDto commentDto = CommentMapper.INSTANCE.toDto(comment);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @RequestMapping(value = "article/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<CommentDto>> getCommentsByArticleId(@PathVariable(name = "id") Long articleId) {
        List<Comment> comments = articleService.getById(articleId).getList();
        List<CommentDto> commentDTOS = comments.stream().map(i -> CommentMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }
}
