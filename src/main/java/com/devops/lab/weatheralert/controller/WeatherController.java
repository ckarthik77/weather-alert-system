package com.devops.lab.weatheralert.controller;

import com.devops.lab.weatheralert.model.AlertResponse;
import com.devops.lab.weatheralert.model.WeatherData;
import com.devops.lab.weatheralert.service.WeatherAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherAlertService weatherService;

    /**
     * Welcome endpoint
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Weather Alert System");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("endpoints", "/api/weather/check/{location}, /api/weather/data/{location}");
        return ResponseEntity.ok(response);
    }

    /**
     * Get weather data for a location
     */
    @GetMapping("/data/{location}")
    public ResponseEntity<WeatherData> getWeatherData(@PathVariable String location) {
        WeatherData data = weatherService.getWeatherData(location);
        return ResponseEntity.ok(data);
    }

    /**
     * Check weather and get alerts for a location
     */
    @GetMapping("/check/{location}")
    public ResponseEntity<AlertResponse> checkWeather(@PathVariable String location) {
        AlertResponse response = weatherService.analyzeWeather(location);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "weather-alert-system");
        health.put("healthy", weatherService.isServiceHealthy());
        health.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(health);
    }
}