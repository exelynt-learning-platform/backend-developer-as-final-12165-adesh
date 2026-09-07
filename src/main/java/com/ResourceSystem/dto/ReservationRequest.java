package com.ResourceSystem.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotNull(message = "Resource Id is required")
    private Long resourceId;

    @NotNull(message = "Start Time is required")
    @Future
    private LocalDateTime startTime;

    @NotNull(message = "End Time is required")
    @Future
    private LocalDateTime endTime;
}
