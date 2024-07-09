package com.warehouse_stocks.demo.services;

import java.util.List;

import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Article createArticle(String name) {
        if (articleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Article already exists");
        }
        return articleRepository.save(new Article(name));
    }

    public void deleteArticle(Long id) {
        Article item = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        articleRepository.delete(item);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}