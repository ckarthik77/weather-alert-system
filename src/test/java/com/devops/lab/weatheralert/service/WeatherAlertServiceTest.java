package com.devops.lab.weatheralert.service;

import com.devops.lab.weatheralert.model.AlertResponse;
import com.devops.lab.weatheralert.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherAlertServiceTest {

    private WeatherAlertService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherAlertService();
    }

    @Test
    void testGetWeatherData() {
        String location = "TestCity";
        WeatherData data = weatherService.getWeatherData(location);
        
        assertNotNull(data);
        assertEquals(location, data.getLocation());
        assertTrue(data.getTemperature() >= 15 && data.getTemperature() <= 40);
        assertTrue(data.getHumidity() >= 30 && data.getHumidity() <= 90);
        assertTrue(data.getWindSpeed() >= 5 && data.getWindSpeed() <= 65);
        assertNotNull(data.getCondition());
        assertTrue(data.getTimestamp() > 0);
    }

    @Test
    void testAnalyzeWeather() {
        String location = "TestCity";
        AlertResponse response = weatherService.analyzeWeather(location);
        
        assertNotNull(response);
        assertNotNull(response.getWeatherData());
        assertNotNull(response.getAlerts());
        assertNotNull(response.getMessage());
        assertEquals(location, response.getWeatherData().getLocation());
    }

    @Test
    void testServiceHealth() {
        assertTrue(weatherService.isServiceHealthy());
    }

    @Test
    void testAlertResponseStructure() {
        AlertResponse response = weatherService.analyzeWeather("Mumbai");
        
        assertNotNull(response.getWeatherData());
        assertTrue(response.getMessage().contains("Mumbai"));
        
        if (response.isAlertTriggered()) {
            assertFalse(response.getAlerts().isEmpty());
        }
    }

    @Test
    void testMultipleLocations() {
        String[] locations = {"Delhi", "Mumbai", "Bangalore", "Chennai"};
        
        for (String location : locations) {
            AlertResponse response = weatherService.analyzeWeather(location);
            assertNotNull(response);
            assertEquals(location, response.getWeatherData().getLocation());
        }
    }
}