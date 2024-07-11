package com.warehouse_stocks.demo.controllers;

import com.warehouse_stocks.demo.controllers.dto.ArticleDTO;
import com.warehouse_stocks.demo.controllers.dto.StockIdDTO;
import com.warehouse_stocks.demo.controllers.dto.StockItemDTO;
import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.entities.StockID;
import com.warehouse_stocks.demo.entities.StockItem;
import com.warehouse_stocks.demo.services.StockItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockItemController.class)
public class StockItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockItemService stockItemService;

    @MockBean
    private ModelMapper modelMapper;

    private StockItemDTO stockItemDTO;
    private StockItem stockItem;

    @BeforeEach
    void setUp() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setName("Sample Article");

        StockIdDTO stockIdDTO = new StockIdDTO();
        stockIdDTO.setArticle(articleDTO);
        stockIdDTO.setLocation("Sample Location");

        stockItemDTO = new StockItemDTO();
        stockItemDTO.setStockID(stockIdDTO);
        stockItemDTO.setQuantity(10);

        Article article = new Article();
        article.setId(1L);
        article.setName("Sample Article");

        StockID stockID = new StockID();
        stockID.setArticle(article);
        stockID.setLocation("Sample Location");

        stockItem = new StockItem();
        stockItem.setStockId(stockID);
        stockItem.setQuantity(10);
    }

    @Test
    void testCreateStockItem() throws Exception {
        when(stockItemService.createStockItem(anyInt(), anyString(), anyString())).thenReturn(stockItem);
        when(modelMapper.map(any(StockItem.class), eq(StockItemDTO.class))).thenReturn(stockItemDTO);

        mockMvc.perform(post("/api/stock-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stockID\":{\"article\":{\"id\":1,\"name\":\"Sample Article\"},\"location\":\"Sample Location\"},\"quantity\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testUpdateStockItem() throws Exception {
        when(stockItemService.updateStockItem(anyString(), anyString(), anyString(), anyInt())).thenReturn(stockItem);
        when(modelMapper.map(any(StockItem.class), eq(StockItemDTO.class))).thenReturn(stockItemDTO);

        mockMvc.perform(put("/api/stock-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"article\":{\"id\":1,\"name\":\"Sample Article\"},\"location\":\"Sample Location\"}")
                        .param("location", "New Location")
                        .param("qty", "20"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testDeleteStockItem() throws Exception {
        doNothing().when(stockItemService).deleteStockItem(anyString(), anyString());

        mockMvc.perform(delete("/api/stock-items/Sample Article/Sample Location"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllStockItems() throws Exception {
        when(stockItemService.getAllStockItems()).thenReturn(Collections.singletonList(stockItem));
        when(modelMapper.map(any(StockItem.class), eq(StockItemDTO.class))).thenReturn(stockItemDTO);

        mockMvc.perform(get("/api/stock-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(10));
    }
}
