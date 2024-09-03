package org.example.trainplanet.exceptions;

public class TrainFullException extends RuntimeException {
    public TrainFullException(Long trainId) {
        super("Train with ID " + trainId + " is full");
    }
}