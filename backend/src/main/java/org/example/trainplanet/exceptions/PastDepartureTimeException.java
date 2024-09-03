package org.example.trainplanet.exceptions;

import java.time.LocalDateTime;

public class PastDepartureTimeException extends RuntimeException {
    public PastDepartureTimeException(LocalDateTime departureTime) {
        super("Cannot book a train that has already departed. Departure time: " + departureTime);
    }
}