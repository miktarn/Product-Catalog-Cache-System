package com.people.pulse.tech.task.model.dto.mapper;

import com.people.pulse.tech.task.model.Product;
import com.people.pulse.tech.task.model.dto.ProductRequestDto;
import com.people.pulse.tech.task.model.dto.ProductResponseDto;

public class ProductMapper {
    public static Product toModel(ProductRequestDto dto) {
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

    public static ProductResponseDto toResponseDto(Product model) {
        return ProductResponseDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .price(model.getPrice())
                .category(model.getCategory())
                .stock(model.getStock())
                .createdDate(model.getCreatedDate())
                .lastUpdatedDate(model.getLastUpdatedDate())
                .build();
    }
}
