package org.example.trainplanet.booking;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingSpecification {

    public static Specification<Booking> searchBookings(
            Long userId,
            Long trainId,
            String status,
            LocalDateTime bookingTimeFrom,
            LocalDateTime bookingTimeTo,
            String departureStation,
            String arrivalStation) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            if (trainId != null) {
                predicates.add(criteriaBuilder.equal(root.get("train").get("id"), trainId));
            }

            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (bookingTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bookingTime"), bookingTimeFrom));
            }

            if (bookingTimeTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bookingTime"), bookingTimeTo));
            }

            if (departureStation != null && !departureStation.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("train").get("departureStation"), departureStation));
            }

            if (arrivalStation != null && !arrivalStation.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("train").get("arrivalStation"), arrivalStation));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}