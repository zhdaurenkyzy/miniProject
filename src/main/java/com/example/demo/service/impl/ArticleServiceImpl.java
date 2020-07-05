package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public Article getById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found with id " + id));
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
