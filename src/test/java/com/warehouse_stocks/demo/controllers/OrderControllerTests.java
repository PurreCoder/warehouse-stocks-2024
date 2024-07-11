package com.warehouse_stocks.demo.controllers;

import com.warehouse_stocks.demo.controllers.dto.OrderDTO;
import com.warehouse_stocks.demo.controllers.dto.OrderLineDTO;
import com.warehouse_stocks.demo.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testCheckOrder() throws Exception {
        OrderLineDTO orderLineDTO = new OrderLineDTO("Sample Article", 5);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderItems(Collections.singletonList(orderLineDTO));

        when(orderService.canFulfillOrder(any())).thenReturn(Collections.singletonList(orderLineDTO));

        mockMvc.perform(post("/api/orders/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderItems\":[{\"articleName\":\"Sample Article\",\"qty\":5}]}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].articleName").value("Sample Article"));
    }
}
