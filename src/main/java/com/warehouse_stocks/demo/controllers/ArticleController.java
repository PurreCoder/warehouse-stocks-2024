package com.warehouse_stocks.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.warehouse_stocks.demo.controllers.dto.ArticleDTO;
import com.warehouse_stocks.demo.services.ArticleService;
import com.warehouse_stocks.demo.entities.Article;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        //Article articleToCreate = modelMapper.map(articleDTO, Article.class);
        Article createdArticle = articleService.createArticle(articleDTO.getName());
        ArticleDTO createdDTO = modelMapper.map(createdArticle, ArticleDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleDTO> articlesDTO = articles.stream().map(record -> modelMapper.map(record, ArticleDTO.class)).toList();
        return ResponseEntity.ok(articlesDTO);
    }


}
