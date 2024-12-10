package com.people.pulse.tech.task.model.dto.mapper;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stock(dto.getStock())
                .createdDate(dto.getCreatedDate())
                .lastUpdatedDate(dto.getLastUpdatedDate())
                .build();
    }
}
