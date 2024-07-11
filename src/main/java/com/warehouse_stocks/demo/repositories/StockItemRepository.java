package com.warehouse_stocks.demo.repositories;

import com.warehouse_stocks.demo.entities.StockID;
import com.warehouse_stocks.demo.entities.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, StockID> {
    @Query(value = "SELECT SUM(s.quantity) FROM STOCK_ITEM s WHERE s.article_id = (SELECT a.id FROM Article a WHERE a.name = :name)", nativeQuery = true)
    Integer getQuantityOverAllLocationsByArticle(String name);
}
