package com.people.pulse.tech.task.controller;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<Product> listAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody ProductRequestDto requestDto) {
        return productService.create(requestDto);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        return productService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getAllByCategory(@PathVariable String category) {
        return productService.getAllByCategory(category);
    }
}
