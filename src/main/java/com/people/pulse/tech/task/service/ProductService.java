package com.people.pulse.tech.task.service;

import com.people.pulse.tech.task.model.Product;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    Product create(Product newProduct);

    Product getById(Long id);

    Product update(Product updatedProduct);

    void deleteById(Long id);

    List<Product> getAllByCategory(String category);

    List<Product> findAll(PageRequest pageRequest);
}
