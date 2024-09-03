package org.example.trainplanet.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Invalid booking status: " + status);
    }
}