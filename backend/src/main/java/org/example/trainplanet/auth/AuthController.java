package org.example.trainplanet.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "User login with email and password schema",
            description = "Authenticate a user using their email and password. If successful, returns a success message. If authentication fails, returns an error message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class),
                            examples = @ExampleObject(value = "{\"message\": \"Login successful for email: user@example.com\"}"))),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class),
                            examples = @ExampleObject(value = "{\"message\": \"Authentication failed: Invalid credentials\"}")))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            authService.authenticateLocal(loginRequest.getEmail(), loginRequest.getPassword(), request);
            response.put("message", "Login successful for email: " + loginRequest.getEmail());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("message", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @Operation(summary = "New user registration with email and password schema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Registration is successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class),
                            examples = @ExampleObject(value = "{\"message\": \"User registered successfully for email: user@example.com\"}"))),
            @ApiResponse(responseCode = "400",
                    description = "Registration failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class),
                            examples = @ExampleObject(value = "{\"message\": \"Registration failed: Email already in use\"}"))),
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            response.put("message", "User registered successfully for email: " + registerRequest.getEmail());
            authService.registerUser(registerRequest, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}