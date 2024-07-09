package com.warehouse_stocks.demo.services;

import com.warehouse_stocks.demo.controllers.dto.OrderLineDTO;
import com.warehouse_stocks.demo.repositories.StockItemRepository;
import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.entities.StockItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private StockItemRepository stockItemRepository;

    public List<OrderLineDTO> canFulfillOrder(List<OrderLineDTO> orderItemsDTO) {
        List<OrderLineDTO> deficitList = new ArrayList<>();
        for (OrderLineDTO orderLine : orderItemsDTO) {
            String curArticle = orderLine.getArticleName();
            Integer cnt = stockItemRepository.getQuantityOverAllLocationsByArticle(curArticle);
            Integer deficit = orderLine.getQty() - cnt;
            if (deficit > 0) {
                deficitList.add(new OrderLineDTO(curArticle, deficit));
            }
        }
        return deficitList;
    }
}
