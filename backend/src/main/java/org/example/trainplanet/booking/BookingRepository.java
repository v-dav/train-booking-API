package org.example.trainplanet.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByTrainId(Long trainId);
    long countByTrainId(Long trainId);
    boolean existsByTrainIdAndSeatNumber(Long trainId, Integer seatNumber);
}