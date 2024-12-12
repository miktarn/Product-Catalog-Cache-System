package com.people.pulse.tech.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.service.ProductServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@WebMvcTest(ProductController.class)
public class ProductControllerValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldBadRequestWhenCreateWithNullName() throws Exception {
        ProductRequestDto request = new ProductRequestDto(null, "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var actual = result.getResolvedException().getClass();
        Assertions.assertEquals(MethodArgumentNotValidException.class, actual);
        verify(productService, never()).create(any());
    }

    @Test
    void shouldBadRequestWhenCreateWithNegativePrice() throws Exception {
        ProductRequestDto negativePriceRequest = new ProductRequestDto("Product1", "Description1", BigDecimal.valueOf(-1), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(negativePriceRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var actual = result.getResolvedException().getClass();
        Assertions.assertEquals(MethodArgumentNotValidException.class, actual);
        verify(productService, never()).create(any());
    }

    @Test
    void shouldBadRequestWhenCreateWithNegativeStock() throws Exception {
        ProductRequestDto request = new ProductRequestDto("Product1", "Description1", BigDecimal.valueOf(100), "Category1", -5, LocalDateTime.now(), LocalDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var actual = result.getResolvedException().getClass();
        Assertions.assertEquals(MethodArgumentNotValidException.class, actual);
        verify(productService, never()).create(any());
    }

    @Test
    void shouldBadRequestWhenCreateWithNullPrice() throws Exception {
        ProductRequestDto request = new ProductRequestDto("Product1", "Description1", null, "Category1", 10, LocalDateTime.now(), LocalDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var actual = result.getResolvedException().getClass();
        Assertions.assertEquals(MethodArgumentNotValidException.class, actual);
        verify(productService, never()).create(any());
    }

}
