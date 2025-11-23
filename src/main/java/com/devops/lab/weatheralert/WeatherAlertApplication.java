package com.devops.lab.weatheralert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherAlertApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAlertApplication.class, args);
        System.out.println("🌤️  Weather Alert System Started Successfully!");
        System.out.println("📊 Health Check: http://localhost:8080/actuator/health");
        System.out.println("🔗 API Endpoint: http://localhost:8080/api/weather");
    }
}