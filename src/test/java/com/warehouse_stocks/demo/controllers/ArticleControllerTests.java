package com.warehouse_stocks.demo.controllers;

import com.warehouse_stocks.demo.controllers.dto.ArticleDTO;
import com.warehouse_stocks.demo.entities.Article;
import com.warehouse_stocks.demo.services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ModelMapper modelMapper;

    private ArticleDTO articleDTO;
    private Article article;

    @BeforeEach
    void setUp() {
        articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setName("Sample Article");

        article = new Article();
        article.setId(1L);
        article.setName("Sample Article");
    }

    @Test
    void testCreateArticle() throws Exception {
        when(articleService.createArticle(anyString())).thenReturn(article);
        when(modelMapper.map(any(Article.class), eq(ArticleDTO.class))).thenReturn(articleDTO);

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sample Article\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Article"));
    }

    @Test
    void testDeleteArticle() throws Exception {
        doNothing().when(articleService).deleteArticle(1L);

        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllArticles() throws Exception {
        when(articleService.getAllArticles()).thenReturn(Collections.singletonList(article));
        when(modelMapper.map(any(Article.class), eq(ArticleDTO.class))).thenReturn(articleDTO);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Article"));
    }
}
