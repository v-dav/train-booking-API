package org.example.trainplanet.exceptions;

public class SeatNotAvailableException extends RuntimeException {
    public SeatNotAvailableException(Long trainId, Integer seatNumber) {
        super("Seat " + seatNumber + " is not available on train with ID " + trainId);
    }
}