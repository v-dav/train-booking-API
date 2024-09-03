package org.example.trainplanet.exceptions;

public class NoUserBookingsFoundException extends RuntimeException {
    public NoUserBookingsFoundException(Long userId) {
        super("No bookings found for user with ID: " + userId);
    }
}