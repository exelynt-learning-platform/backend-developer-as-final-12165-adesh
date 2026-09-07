package com.ResourceSystem.service;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import com.ResourceSystem.dto.ReservationRequest;
import com.ResourceSystem.dto.ReservationResponse;
import com.ResourceSystem.entity.ReservationStatus;

public interface ReservationService {
        ReservationResponse createReservation(ReservationRequest request);

        ReservationResponse getReservationById(
                        Long reservationId);

        // Page<ReservationResponse> getMyReservations(Pageable pageable);

        Page<ReservationResponse> getReservations(

                        ReservationStatus status,

                        BigDecimal minPrice,

                        BigDecimal maxPrice,

                        int page,

                        int size,

                        String sortBy,

                        String direction);

        ReservationResponse updateStatus(Long id, ReservationStatus status);

        void deleteReservation(Long id);
}
