package com.warehouse_stocks.demo.repositories;
import com.warehouse_stocks.demo.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ArticleRepository1 extends JpaRepository<Article, Long> {

    Optional<Article> findByName(String name);
}
