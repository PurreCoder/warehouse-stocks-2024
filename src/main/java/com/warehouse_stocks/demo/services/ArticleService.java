package com.warehouse_stocks.demo.services;

import java.util.List;

import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.repositories.ArticleRepository1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleService {
    @Autowired
    private ArticleRepository1 articleRepository1;

    public Article createArticle(String name) {
        if (articleRepository1.findByName(name).isPresent()) {
            throw new RuntimeException("Article already exists");
        }
        return articleRepository1.save(new Article(name));
    }

    public void deleteArticle(Long id) {
        Article item = articleRepository1.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        articleRepository1.delete(item);
    }

    public List<Article> getAllArticles() {
        return articleRepository1.findAll();
    }
}