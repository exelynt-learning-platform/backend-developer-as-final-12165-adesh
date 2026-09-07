package com.ResourceSystem.serviceImpl;

import java.math.BigDecimal;
import java.time.Duration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.ResourceSystem.dto.ReservationRequest;
import com.ResourceSystem.dto.ReservationResponse;
import com.ResourceSystem.entity.Reservation;
import com.ResourceSystem.entity.ReservationStatus;
import com.ResourceSystem.entity.Resource;
import com.ResourceSystem.entity.User;
import com.ResourceSystem.exception.BadRequestException;
import com.ResourceSystem.exception.ResourceNotFoundException;
import com.ResourceSystem.repository.ReservationRepository;
import com.ResourceSystem.repository.ResourceRepository;
import com.ResourceSystem.repository.UserRepository;
import com.ResourceSystem.service.ReservationService;
import com.ResourceSystem.specification.ReservationSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

        private final ReservationRepository reservationRepository;

        private final ResourceRepository resourceRepository;

        private final UserRepository userRepository;

        @Override
        public ReservationResponse createReservation(ReservationRequest request) {
                User currentUser = getCurrentUser();

                Resource resource = resourceRepository.findById(
                                request.getResourceId())

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Resource not found"));

                if (!resource.getAvailable()) {

                        throw new BadRequestException(
                                        "Resource is not available");

                }

                if (request.getStartTime()
                                .isAfter(request.getEndTime())) {

                        throw new BadRequestException(
                                        "Start time must be before end time");

                }

                /*
                 * Price calculation
                 * hours * price per hour
                 */
                long hours = Duration.between(
                                request.getStartTime(),
                                request.getEndTime())
                                .toHours();

                if (hours <= 0) {

                        hours = 1;

                }

                BigDecimal price = resource.getPricePerHour()
                                .multiply(
                                                BigDecimal.valueOf(hours));

                Reservation reservation = Reservation.builder()

                                .startTime(
                                                request.getStartTime())

                                .endTime(
                                                request.getEndTime())

                                .price(price)

                                .status(
                                                ReservationStatus.PENDING)

                                .user(currentUser)

                                .resource(resource)

                                .build();

                Reservation saved = reservationRepository.save(
                                reservation);

                return mapToResponse(saved);

        }

        @Override
        public ReservationResponse getReservationById(Long reservationId) {
                Reservation reservation = reservationRepository.findById(
                                reservationId)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Reservation not found"));

                User currentUser = getCurrentUser();

                /*
                 * USER can access only own reservation
                 */
                if (!isAdmin()
                                &&
                                !reservation.getUser()
                                                .getId()
                                                .equals(currentUser.getId())) {

                        throw new BadRequestException(
                                        "You cannot access this reservation");

                }

                return mapToResponse(reservation);

        }

        @Override
        public Page<ReservationResponse> getReservations(ReservationStatus status, BigDecimal minPrice,
                        BigDecimal maxPrice,
                        int page, int size, String sortBy, String direction) {
                // TODO Auto-generated method stub
                Pageable pageable;

                if (direction.equalsIgnoreCase("desc")) {

                        pageable = PageRequest.of(
                                        page,
                                        size,
                                        Sort.by(sortBy).descending());

                } else {

                        pageable = PageRequest.of(
                                        page,
                                        size,
                                        Sort.by(sortBy).ascending());

                }

                Specification<Reservation> specification =

                                Specification
                                                .where(
                                                                ReservationSpecification.hasStatus(status))

                                                .and(
                                                                ReservationSpecification.hasMinPrice(minPrice))

                                                .and(
                                                                ReservationSpecification.hasMaxPrice(maxPrice));

                /*
                 * USER sees only own reservations
                 */
                if (!isAdmin()) {

                        User user = getCurrentUser();

                        specification = specification.and(

                                        ReservationSpecification
                                                        .belongsToUser(user.getId())

                        );

                }

                return reservationRepository
                                .findAll(
                                                specification,
                                                pageable)

                                .map(
                                                this::mapToResponse);
        }

        @Override
        public ReservationResponse updateStatus(Long id, ReservationStatus status) {

                Reservation reservation = reservationRepository.findById(
                                id)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Reservation not found"));
                reservation.setStatus(status);

                return mapToResponse(
                                reservationRepository.save(
                                                reservation));

        }

        @Override
        public void deleteReservation(Long id) {
                Reservation reservation = reservationRepository.findById(
                                id)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "Reservation not found"));

                User user = getCurrentUser();

                if (!isAdmin()
                                &&
                                !reservation.getUser()
                                                .getId()
                                                .equals(user.getId())) {

                        throw new BadRequestException(
                                        "You cannot cancel this reservation");

                }

                reservation.setStatus(
                                ReservationStatus.CANCELLED);

                reservationRepository.save(
                                reservation);

        }

        private User getCurrentUser() {

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                String email = authentication.getName();

                return userRepository
                                .findByEmail(email)

                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "User not found"));

        }

        private boolean isAdmin() {

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                return authentication
                                .getAuthorities()
                                .stream()
                                .anyMatch(authority -> authority.getAuthority()
                                                .equals("ROLE_ADMIN"));

        }

        private ReservationResponse mapToResponse(
                        Reservation reservation) {

                return ReservationResponse.builder()

                                .id(
                                                reservation.getId())

                                .resourceName(
                                                reservation.getResource()
                                                                .getName())

                                .userName(
                                                reservation.getUser()
                                                                .getName())

                                .startTime(
                                                reservation.getStartTime())

                                .endTime(
                                                reservation.getEndTime())

                                .status(
                                                reservation.getStatus())

                                .price(
                                                reservation.getPrice())

                                .build();

        }

}
