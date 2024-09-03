package org.example.trainplanet.train;

import org.example.trainplanet.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train getTrainById(Long id) {
        return trainRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Train"));
    }

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }

    public Train updateTrain(Long id, Train trainDetails) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Train"));

        if (trainDetails.getName() != null) {
            train.setName(trainDetails.getName());
        }
        if (trainDetails.getDepartureStation() != null) {
            train.setDepartureStation(trainDetails.getDepartureStation());
        }
        if (trainDetails.getArrivalStation() != null) {
            train.setArrivalStation(trainDetails.getArrivalStation());
        }
        if (trainDetails.getDepartureTime() != null) {
            train.setDepartureTime(trainDetails.getDepartureTime());
        }
        if (trainDetails.getArrivalTime() != null) {
            train.setArrivalTime(trainDetails.getArrivalTime());
        }
        if (trainDetails.getCapacity() != null) {
            train.setCapacity(trainDetails.getCapacity());
        }
        if (trainDetails.getType() != null) {
            train.setType(trainDetails.getType());
        }

        return trainRepository.save(train);
    }

    public void deleteTrain(Long id) {
        boolean exists = trainRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(id, "Train");
        }
        trainRepository.deleteById(id);
    }

    public List<Train> searchTrains(String departureStation, String arrivalStation,
                                    LocalDateTime departureTime, LocalDateTime arrivalTime,
                                    String type) {
        return trainRepository.findAll(TrainSpecification.searchTrains(departureStation, arrivalStation, departureTime, arrivalTime, type));
    }
}