package com.devops.lab.weatheralert.model;

import java.util.List;

public class AlertResponse {
    private boolean alertTriggered;
    private List<String> alerts;
    private WeatherData weatherData;
    private String message;

    // Default constructor
    public AlertResponse() {
    }

    // Constructor with all fields
    public AlertResponse(boolean alertTriggered, List<String> alerts, 
                        WeatherData weatherData, String message) {
        this.alertTriggered = alertTriggered;
        this.alerts = alerts;
        this.weatherData = weatherData;
        this.message = message;
    }

    // Getters and Setters
    public boolean isAlertTriggered() {
        return alertTriggered;
    }

    public void setAlertTriggered(boolean alertTriggered) {
        this.alertTriggered = alertTriggered;
    }

    public List<String> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<String> alerts) {
        this.alerts = alerts;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}