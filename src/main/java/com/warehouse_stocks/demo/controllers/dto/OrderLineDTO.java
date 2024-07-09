package com.warehouse_stocks.demo.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderLineDTO {
    String articleName;
    Integer qty;
}
