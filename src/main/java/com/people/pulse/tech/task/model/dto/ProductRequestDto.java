package com.people.pulse.tech.task.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stock;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
