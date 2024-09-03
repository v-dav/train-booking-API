package org.example.trainplanet.train;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.trainplanet.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("train")
public class TrainController {

    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @Operation(summary = "Get all trains", description = "Retrieve a list of all trains in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of trains retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Train.class))),
            @ApiResponse(responseCode = "204", description = "No trains found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        if (trains.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trains, HttpStatus.OK);
    }


    @Operation(summary = "Get train by ID", description = "Retrieve a train by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Train.class))),
            @ApiResponse(responseCode = "404", description = "Train not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Train with ID 123 does not exist\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable Long id) {
        try {
            Train train = trainService.getTrainById(id);
            return ResponseEntity.ok(train);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new train", description = "Create a new train with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Train created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Train.class))),
            @ApiResponse(responseCode = "409", description = "Train creation conflict",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Train with the same name already exists\"}")))
    })
    @PostMapping
    public ResponseEntity<?> createTrain(@RequestBody Train train) {
        try {
            Train newTrain = trainService.saveTrain(train);
            return new ResponseEntity<>(newTrain, HttpStatus.CREATED); // 201
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409
        }
    }

    @Operation(summary = "Update train details", description = "Update specific details of an existing train by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Train updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid field(s) provided",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Invalid field value\"}"))),
            @ApiResponse(responseCode = "404", description = "Train not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Train with ID 123 doesnt exist\"}")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrain(@PathVariable Long id, @RequestBody Train trainDetails) {
        Map<String, String> response = new HashMap<>();
        try {
            Train updatedTrain = trainService.updateTrain(id, trainDetails);
            return new ResponseEntity<>(updatedTrain, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request for invalid fields
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found for non-existent user
        }
    }

    @Operation(summary = "Delete a train", description = "Delete a train by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Train deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Train not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Train with ID 123 doesnt exist\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable Long id) {
        try {
            trainService.deleteTrain(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Train deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT); // 204
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not found
        }
    }

    @Operation(summary = "Search for trains", description = "Search for trains based on various criteria such as departure station, arrival station, departure time, arrival time, and type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trains found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Train.class))),
            @ApiResponse(responseCode = "204", description = "No trains found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid search criteria",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Invalid request data\"}")))
    })
    @GetMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(
            @RequestParam(required = false) String departureStation,
            @RequestParam(required = false) String arrivalStation,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime,
            @RequestParam(required = false) String type) {

        List<Train> trains = trainService.searchTrains(departureStation, arrivalStation,
                departureTime, arrivalTime, type);

        if (trains.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(trains, HttpStatus.OK);
    }
}