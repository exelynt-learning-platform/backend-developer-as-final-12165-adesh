package com.ResourceSystem.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ResourceSystem.entity.Reservation;
import com.ResourceSystem.entity.ReservationStatus;

import java.math.BigDecimal;

public class ReservationSpecification {

    private ReservationSpecification() {
    }

    public static Specification<Reservation> hasStatus(
            ReservationStatus status) {

        return (root, query, cb) -> {

            if (status == null) {
                return null;
            }

            return cb.equal(
                    root.get("status"),
                    status);
        };
    }

    public static Specification<Reservation> hasMinPrice(
            BigDecimal minPrice) {

        return (root, query, cb) -> {

            if (minPrice == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(
                    root.get("price"),
                    minPrice);
        };
    }

    public static Specification<Reservation> hasMaxPrice(
            BigDecimal maxPrice) {

        return (root, query, cb) -> {

            if (maxPrice == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(
                    root.get("price"),
                    maxPrice);
        };
    }

    public static Specification<Reservation> belongsToUser(Long userId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("user").get("id"),
                userId);

    }
}
