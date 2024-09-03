package org.example.trainplanet.booking;

import jakarta.transaction.Transactional;
import org.example.trainplanet.exceptions.*;
import org.example.trainplanet.train.Train;
import org.example.trainplanet.train.TrainService;
import org.example.trainplanet.user.User;
import org.example.trainplanet.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final TrainService trainService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserService userService, TrainService trainService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.trainService = trainService;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Booking"));
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        userService.getUserById(userId);
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        if (bookings.isEmpty()) {
            throw new NoUserBookingsFoundException(userId);
        }
        return bookings;
    }

    public List<Booking> getBookingsByTrainId(Long trainId) {
        trainService.getTrainById(trainId);
        List<Booking> bookings = bookingRepository.findByTrainId(trainId);
        if (bookings.isEmpty()) {
            throw new NoTrainBookingsFoundException(trainId);
        }
        return bookings;
    }

    @Transactional
    public Booking createBooking(Long userId, Long trainId, Integer seatNumber) {
        User user = userService.getUserById(userId);
        Train train = trainService.getTrainById(trainId);

        // Check if the train is full
        if (bookingRepository.countByTrainId(trainId) >= train.getCapacity()) {
            throw new TrainFullException(trainId);
        }

        // Check if the seat is available
        if (bookingRepository.existsByTrainIdAndSeatNumber(trainId, seatNumber)) {
            throw new SeatNotAvailableException(trainId, seatNumber);
        }

        // Check if the seat number is valid
        if (seatNumber <= 0 || seatNumber > train.getCapacity()) {
            throw new InvalidSeatNumberException(seatNumber, train.getCapacity());
        }

        // Check if the departure time is in the future
        if (train.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new PastDepartureTimeException(train.getDepartureTime());
        }

        Booking booking = new Booking(user, train, LocalDateTime.now(), "CONFIRMED", seatNumber);
        return bookingRepository.save(booking);
    }


    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Booking"));

        if (!isValidStatus(status)) {
            throw new InvalidStatusException(status);
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    private boolean isValidStatus(String status) {
        List<String> validStatuses = Arrays.asList("CONFIRMED", "CANCELLED", "COMPLETED");
        return validStatuses.contains(status.toUpperCase());
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException(id, "Booking");
        }
        bookingRepository.deleteById(id);
    }

    public List<Booking> searchBookings(
            Long userId,
            Long trainId,
            String status,
            LocalDateTime bookingTimeFrom,
            LocalDateTime bookingTimeTo,
            String departureStation,
            String arrivalStation) {

        return bookingRepository.findAll(BookingSpecification.searchBookings(
                userId, trainId, status, bookingTimeFrom, bookingTimeTo, departureStation, arrivalStation));
    }
}