package com.warehouse_stocks.demo.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockIdDTO {
    ArticleDTO article;
    String location;
}
