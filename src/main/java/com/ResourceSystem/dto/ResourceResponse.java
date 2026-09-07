package com.ResourceSystem.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceResponse {
    private Long id;

    private String name;

    private String type;

    private String description;

    private String location;

    private BigDecimal price;

    private Boolean available;

}
