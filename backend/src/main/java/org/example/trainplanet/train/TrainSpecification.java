package org.example.trainplanet.train;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainSpecification {

    public static Specification<Train> searchTrains(
            String departureStation,
            String arrivalStation,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            String type) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departureStation != null && !departureStation.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("departureStation"), departureStation));
            }

            if (arrivalStation != null && !arrivalStation.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("arrivalStation"), arrivalStation));
            }

            if (departureTime != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("departureTime"), departureTime));
            }

            if (arrivalTime != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("arrivalTime"), arrivalTime));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}