package com.ResourceSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ResourceSystem.entity.ReservationStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponse {
    private Long id;

    private String resourceName;

    private String userName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ReservationStatus status;

    private BigDecimal price;
}
