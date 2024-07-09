package com.warehouse_stocks.demo.repositories;

import com.warehouse_stocks.demo.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByName(String name);
}
