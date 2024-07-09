package com.warehouse_stocks.demo.repositories;

import com.warehouse_stocks.demo.entities.StockID;
import com.warehouse_stocks.demo.entities.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockItemRepository extends JpaRepository<StockItem, StockID> {
    @Query(value="Select SUM(s.quantity) FROM StockItem s JOIN Article a on s.article_id = a.id WHERE a.name = :name")
    Integer getQuantityOverAllLocationsByArticle(String name);
    //Optional<StockItem> findByNameAndLoc(Long id, String location);
}