package com.people.pulse.tech.task.controller;

import static com.people.pulse.tech.task.model.dto.mapper.ProductMapper.toModel;
import static com.people.pulse.tech.task.model.dto.mapper.ProductMapper.toResponseDto;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.model.dto.ProductResponseDto;
import com.people.pulse.tech.task.model.dto.mapper.ProductMapper;
import com.people.pulse.tech.task.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20") @Positive Integer count,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer page) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAll(pageRequest).stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable @Positive Long id) {
        return toResponseDto(productService.getById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@RequestBody @Valid ProductRequestDto requestDto) {
        Product created = productService.create(toModel(requestDto));
        return toResponseDto(created);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable @Positive Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = toModel(requestDto);
        product.setId(id);
        return toResponseDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getAllByCategory(@PathVariable @NotBlank String category) {
        return productService.getAllByCategory(category);
    }
}
