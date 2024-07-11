package com.warehouse_stocks.demo.controllers;

import com.warehouse_stocks.demo.controllers.dto.ArticleDTO;
import com.warehouse_stocks.demo.controllers.dto.StockIdDTO;
import com.warehouse_stocks.demo.controllers.dto.StockItemDTO;
import com.warehouse_stocks.demo.services.StockItemService;
import com.warehouse_stocks.demo.entities.StockItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-items")
public class StockItemController {
    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<StockItemDTO> createStockItem(@RequestBody StockItemDTO stockItemDTO) {
        StockItem createdStockItem = stockItemService.createStockItem(
                stockItemDTO.getQuantity(), stockItemDTO.getStockID().getArticle().getName(), stockItemDTO.getStockID().getLocation());
        StockItemDTO createdDTO = modelMapper.map(createdStockItem, StockItemDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @PutMapping("/{location}/{qty}")
    public ResponseEntity<StockItemDTO> updateStockItem(@RequestBody StockIdDTO stockItemIdDTO, @PathVariable String location, @PathVariable Integer qty) {

        StockItem createdStockItem = stockItemService.updateStockItem(stockItemIdDTO.getArticle().getName(), stockItemIdDTO.getLocation(), location, qty);
        StockItemDTO createdDTO = modelMapper.map(createdStockItem, StockItemDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/{location}/{name}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable String name, @PathVariable String location) {
        stockItemService.deleteStockItem(name, location);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StockItemDTO>> getAllStockItems() {
        List<StockItem> stockItems = stockItemService.getAllStockItems();
        List<StockItemDTO> stockItemsDTOs = stockItems.stream().map(record -> modelMapper.map(record, StockItemDTO.class)).toList();
        return ResponseEntity.ok(stockItemsDTOs);
    }
}
