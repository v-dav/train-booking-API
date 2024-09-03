package org.example.trainplanet.exceptions;

public class InvalidSeatNumberException extends RuntimeException {
    public InvalidSeatNumberException(Integer seatNumber, Integer maxCapacity) {
        super("Invalid seat number: " + seatNumber + ". Seat number should be between 1 and " + maxCapacity);
    }
}