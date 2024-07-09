package com.warehouse_stocks.demo.controllers;

import java.util.List;
import java.util.Map;

import com.warehouse_stocks.demo.controllers.dto.OrderDTO;
import com.warehouse_stocks.demo.controllers.dto.OrderLineDTO;
import com.warehouse_stocks.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/check")
    public ResponseEntity<List<OrderLineDTO>> checkOrder(@RequestBody OrderDTO orderDTO) {
        List<OrderLineDTO> canFulfill = orderService.canFulfillOrder(orderDTO.getOrderItems());
        if (canFulfill.isEmpty()) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(canFulfill);
        }
    }
}
