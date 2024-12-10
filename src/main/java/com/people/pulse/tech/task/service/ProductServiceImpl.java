package com.people.pulse.tech.task.service;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.model.dto.mapper.ProductMapper;
import com.people.pulse.tech.task.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product create(ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found product with id:" + id));
    }

    @Override
    public Product update(Long id, ProductRequestDto requestDto) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found product with id:" + id);
        }
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }
}
