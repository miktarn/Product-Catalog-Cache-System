package com.people.pulse.tech.task.controller;

import static com.people.pulse.tech.task.model.dto.mapper.ProductMapper.toResponseDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.model.dto.ProductResponseDto;
import com.people.pulse.tech.task.service.ProductServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;


    Product testProduct = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());
    Product secondTestProduct = new Product(2L, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());
    ProductRequestDto testRequest = new ProductRequestDto("Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());
    ProductResponseDto testResponse = toResponseDto(testProduct);
    ProductResponseDto secondTestResponse = toResponseDto(secondTestProduct);


    @Test
    void shouldFindAllProducts() throws Exception {
        List<ProductResponseDto> expected = List.of(testResponse, secondTestResponse);
        PageRequest testPageRequest = PageRequest.of(0, 20);
        when(productService.findAll(testPageRequest))
                .thenReturn(List.of(testProduct, secondTestProduct));

        MvcResult result = mockMvc.perform(get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<ProductResponseDto> actual = objectMapper.readValue(json, new TypeReference<>() {
        });
        Assertions.assertEquals(expected, actual);
        verify(productService).findAll(testPageRequest);
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(productService.getById(testProduct.getId())).thenReturn(testProduct);

        MvcResult result = mockMvc.perform(get("/api/v1/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ProductResponseDto actual = objectMapper.readValue(json, ProductResponseDto.class);
        Assertions.assertEquals(testResponse, actual);
        verify(productService).getById(testProduct.getId());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.create(any())).thenReturn(testProduct);

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ProductResponseDto actual = objectMapper.readValue(json, ProductResponseDto.class);
        Assertions.assertEquals(testResponse, actual);
        verify(productService).create(any());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.update(any())).thenReturn(testProduct);

        MvcResult result = mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ProductResponseDto actual = objectMapper.readValue(json, ProductResponseDto.class);
        Assertions.assertEquals(testResponse, actual);
        verify(productService).update(any());
    }

    @Test
    void shouldDeleteProductAndReturnNoContent() throws Exception {
        long testId = 1L;
        Mockito.doNothing().when(productService).deleteById(testId);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
        verify(productService).deleteById(testId);
    }

    @Test
    void shouldGetProductsByCategory() throws Exception {
        List<Product> products = List.of(testProduct);

        when(productService.getAllByCategory(testProduct.getCategory())).thenReturn(products);

        MvcResult result = mockMvc.perform(get("/api/v1/products/category/Category1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<ProductResponseDto> actual = objectMapper.readValue(json, new TypeReference<>() {
        });
        Assertions.assertEquals(testResponse, actual.get(0));
        verify(productService).getAllByCategory(testProduct.getCategory());
    }
}
