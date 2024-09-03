package org.example.trainplanet;

import org.example.trainplanet.configs.DatabaseConfigProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableConfigurationProperties(DatabaseConfigProperties.class)
@OpenAPIDefinition(info = @Info(title = "Trainplanet Demo", version = "1.0.0", description = "A fullstack train booking application"))
@RestController
@RequestMapping("/")
public class TrainplanetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainplanetApplication.class, args);
    }

    @GetMapping("status")
    public ResponseEntity<?> status() {
        HashMap<String, String> response = new HashMap<>();
        response.put("response", "Back-end is online");
        return ResponseEntity.ok(response);
    }
}
