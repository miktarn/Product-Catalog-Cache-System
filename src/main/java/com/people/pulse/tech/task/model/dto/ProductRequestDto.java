package com.people.pulse.tech.task.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotNull
    private String name;
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    private String category;
    @PositiveOrZero
    private Integer stock;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
