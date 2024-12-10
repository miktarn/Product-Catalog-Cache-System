package com.people.pulse.tech.task.service;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import java.util.List;

public interface ProductService {
    List<Product> listAllProducts();

    Product create(ProductRequestDto requestDto);

    Product getById(Long id);

    Product update(Long id, ProductRequestDto requestDto);

    void deleteById(Long id);

    List<Product> getAllByCategory(String category);
}
