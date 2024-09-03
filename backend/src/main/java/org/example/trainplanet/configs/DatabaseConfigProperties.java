package org.example.trainplanet.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.datasource")
public record DatabaseConfigProperties(String url, String username, String password) { }
