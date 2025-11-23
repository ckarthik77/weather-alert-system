package com.devops.lab.weatheralert.service;

import com.devops.lab.weatheralert.model.AlertResponse;
import com.devops.lab.weatheralert.model.WeatherData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WeatherAlertService {

    private static final double TEMP_ALERT_THRESHOLD = 35.0; // Celsius
    private static final double HUMIDITY_ALERT_THRESHOLD = 80.0; // Percentage
    private static final double WIND_SPEED_ALERT_THRESHOLD = 50.0; // km/h

    private final Random random = new Random();

    /**
     * Simulates weather data for a given location
     */
    public WeatherData getWeatherData(String location) {
        WeatherData data = new WeatherData();
        data.setLocation(location);
        data.setTemperature(15 + random.nextDouble() * 25); // 15-40°C
        data.setHumidity(30 + random.nextDouble() * 60); // 30-90%
        data.setWindSpeed(5 + random.nextDouble() * 60); // 5-65 km/h
        data.setCondition(getRandomCondition());
        data.setTimestamp(System.currentTimeMillis());
        return data;
    }

    /**
     * Analyzes weather data and generates alerts
     */
    public AlertResponse analyzeWeather(String location) {
        WeatherData weather = getWeatherData(location);
        List<String> alerts = new ArrayList<>();
        boolean alertTriggered = false;

        // Check temperature
        if (weather.getTemperature() > TEMP_ALERT_THRESHOLD) {
            alerts.add("⚠️ HIGH TEMPERATURE ALERT: " + 
                String.format("%.1f°C exceeds threshold of %.1f°C", 
                weather.getTemperature(), TEMP_ALERT_THRESHOLD));
            alertTriggered = true;
        }

        // Check humidity
        if (weather.getHumidity() > HUMIDITY_ALERT_THRESHOLD) {
            alerts.add("⚠️ HIGH HUMIDITY ALERT: " + 
                String.format("%.1f%% exceeds threshold of %.1f%%", 
                weather.getHumidity(), HUMIDITY_ALERT_THRESHOLD));
            alertTriggered = true;
        }

        // Check wind speed
        if (weather.getWindSpeed() > WIND_SPEED_ALERT_THRESHOLD) {
            alerts.add("⚠️ HIGH WIND ALERT: " + 
                String.format("%.1f km/h exceeds threshold of %.1f km/h", 
                weather.getWindSpeed(), WIND_SPEED_ALERT_THRESHOLD));
            alertTriggered = true;
        }

        String message = alertTriggered ? 
            "Weather alerts detected for " + location : 
            "Weather conditions normal for " + location;

        return new AlertResponse(alertTriggered, alerts, weather, message);
    }

    private String getRandomCondition() {
        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Stormy", "Windy", "Clear"};
        return conditions[random.nextInt(conditions.length)];
    }

    /**
     * Health check for service
     */
    public boolean isServiceHealthy() {
        return true;
    }
}