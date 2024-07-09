package com.warehouse_stocks.demo.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class StockItem {
    @EmbeddedId
    StockID stockId;
    Integer quantity;
}