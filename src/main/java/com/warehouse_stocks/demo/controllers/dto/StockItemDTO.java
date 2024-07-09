package com.warehouse_stocks.demo.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemDTO {
    StockIdDTO stockID;
    Integer quantity;
}
