package com.warehouse_stocks.demo.services;

import java.util.List;
import java.util.Optional;

import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.entities.StockID;
import com.warehouse_stocks.demo.repositories.StockItemRepository;
import com.warehouse_stocks.demo.entities.StockItem;
import com.warehouse_stocks.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockItemService {
    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public StockItem createStockItem(Integer qty, String name, String location) {
        Article article = articleRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Article not found"));
        StockID stockID = new StockID(article, location);
        Optional<StockItem> foundItemOpt = stockItemRepository.findById(stockID);
        if (foundItemOpt.isPresent()) {
            StockItem foundItem = foundItemOpt.get();
            foundItem.setQuantity(foundItem.getQuantity() + qty);
            return stockItemRepository.save(foundItem);
        }
        return stockItemRepository.save(new StockItem(stockID, qty));
    }

    public void deleteStockItem(String name, String location) {
        Article article = articleRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Article not found"));
        StockID stockID = new StockID(article, location);
        StockItem foundItem = stockItemRepository.findById(stockID).orElseThrow(()
                -> new RuntimeException("StockItem not found"));
        stockItemRepository.delete(foundItem);
    }

    public StockItem updateStockItem(String articleName, String locationOld, String locationNew, Integer qty){
        Article article = articleRepository.findByName(articleName).orElseThrow(
                () -> new RuntimeException("Article not found"));
        StockID stockID = new StockID(article, locationOld);
        StockItem foundItem = stockItemRepository.findById(stockID).orElseThrow(()
                -> new RuntimeException("StockItem not found"));
        if (qty != null){
            foundItem.setQuantity(qty);
        }
        if (locationNew != null) {
            stockItemRepository.deleteById(foundItem.getStockId());
            foundItem.getStockId().setLocation(locationNew);
        }
        return stockItemRepository.save(foundItem);
    }

    public List<StockItem> getAllStockItems() {
        return stockItemRepository.findAll();
    }
}
