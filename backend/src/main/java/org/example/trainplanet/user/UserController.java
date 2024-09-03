package org.example.trainplanet.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User controller - API layer
 */
@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get list of users",
            description = "Retrieve a list of all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "204", description = "No users found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class)))
    })
    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user ID format",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Invalid user ID format: xyz\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"User with ID 123 not found\"}")))
    })
    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Search for users", description = "Search for users based on various criteria such as email, firstname, lastname, role, etc. Returns the list of users found and the total count.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class),
                            examples = @ExampleObject(value = "{ \"count\": 1, \"users\": [ { \"id\": 24, \"email\": \"123@googlar.com\", \"firstname\": \"Vladimir\", \"lastname\": \"Dadidou\", \"role\": \"ROLE_USER\", \"firstLogin\": \"2024-09-02T14:08:39.796907\", \"lastLogin\": \"2024-09-02T14:08:39.737044\", \"authMethod\": \"LOCAL\" } ] }"))),
            @ApiResponse(responseCode = "204", description = "No users found"),
            @ApiResponse(responseCode = "400", description = "Invalid search criteria",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Invalid request data\"}")))
    })
    @PostMapping("search")
    public ResponseEntity<?> searchUsers(@RequestBody UserSearchCriteria criteria) {
        List<User> users = userService.searchUsers(criteria);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Create the response map
        Map<String, Object> response = new HashMap<>();
        response.put("count", users.size());
        response.put("users", users);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new user", description = "Create a new user account with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"User already exists or there is a conflict with the data\"}")))
    })
    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        try {
            User newUser = userService.postUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED); // 201
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409
        }
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"User with ID 123 does not exist\"}")))
    })
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT); // 204
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not found
        }
    }

    @Operation(summary = "Update user details", description = "Update specific details of an existing user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid field(s) provided",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Field role is not supported for update.\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"User with ID 123 does not exist\"}")))
    })
    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long id,
            @RequestBody Map<String, Object> updates) {
        Map<String, String> response = new HashMap<>();
        try {
            User updatedUser = userService.updateUser(id, updates);
            return new ResponseEntity<>(updatedUser, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request for invalid fields
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found for non-existent user
        }
    }
}
