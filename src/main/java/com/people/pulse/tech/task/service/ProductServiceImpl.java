package com.people.pulse.tech.task.service;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = Product.CACHE_NAME)
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    @CachePut
    public Product create(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Override
    @Cacheable
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found product with id:" + id));
    }

    @Override
    @CacheEvict(allEntries = true)
    public Product update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new EntityNotFoundException("Not found product with id:" + product.getId());
        }
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Cacheable
    public List<Product> getAllByCategory(String category) {
        try {
            Thread.sleep(3_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return productRepository.getProductsByCategory(category);
    }
}
