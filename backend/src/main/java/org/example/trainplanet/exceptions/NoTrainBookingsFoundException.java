package org.example.trainplanet.exceptions;

public class NoTrainBookingsFoundException extends RuntimeException {
    public NoTrainBookingsFoundException(Long userId) {
        super("No bookings found for train with ID: " + userId);
    }
}
