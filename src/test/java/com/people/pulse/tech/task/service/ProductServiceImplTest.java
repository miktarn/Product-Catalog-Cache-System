package com.people.pulse.tech.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    Product testProduct = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());

    @Test
    void shouldFindAllProductsSuccessfully() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now()),
                new Product(2L, "Product2", "Description2", BigDecimal.valueOf(200), "Category2", 20, LocalDateTime.now(), LocalDateTime.now())
        );
        when(productRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(products));

        List<Product> result = productService.findAll(pageRequest);

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        Product newProduct = new Product(null, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());
        Product savedProduct = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100), "Category1", 10, LocalDateTime.now(), LocalDateTime.now());
        when(productRepository.save(newProduct)).thenReturn(savedProduct);

        Product result = productService.create(newProduct);

        assertEquals(savedProduct, result);
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Product result = productService.getById(1L);

        assertEquals(testProduct, result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productService.getById(1L));

        assertEquals("Not found product with id:1", exception.getMessage());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product result = productService.update(testProduct);

        assertEquals(testProduct, result);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        when(productRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productService.update(testProduct)
        );

        assertEquals("Not found product with id:1", exception.getMessage());
    }

    @Test
    void shouldDeleteProductByIdSuccessfully() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetAllProductsByCategorySuccessfully() {
        List<Product> products = Collections.singletonList(testProduct);
        when(productRepository.getProductsByCategory("Category1")).thenReturn(products);

        List<Product> result = productService.getAllByCategory("Category1");

        assertEquals(1, result.size());
        assertEquals("Category1", result.get(0).getCategory());
        verify(productRepository, times(1)).getProductsByCategory("Category1");
    }
}