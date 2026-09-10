# 🌦️ Weather Alert System

A DevOps-focused **Weather Monitoring & Alerting service** built with Spring Boot, demonstrating a complete CI/CD pipeline — from source control to a containerized deployment — using **Jenkins**, **Maven**, and **Docker**. A lightweight static frontend (deployed on Vercel) consumes the REST API to visualize live conditions and alerts.

**🔗 Live demo:** [weather-alert-system-three.vercel.app](https://weather-alert-system-three.vercel.app)

---

## 📋 Overview

The service simulates real-time weather readings for any location and evaluates them against configurable thresholds to raise alerts for extreme conditions — high temperature, high humidity, or high wind speed. It's designed as a hands-on DevOps lab project: the emphasis is as much on the **build → test → containerize → deploy pipeline** as on the application itself.

## ✨ Features

- **REST API** for fetching simulated weather data and alert status per location
- **Threshold-based alerting** for temperature, humidity, and wind speed
- **Health & metrics endpoints** via Spring Boot Actuator
- **Automated CI/CD pipeline** with Jenkins (build → test → Docker image → deploy)
- **Multi-stage Docker build** for a lean, production-ready runtime image
- **Unit tests** with JUnit 5 and code coverage via JaCoCo
- **Static frontend** for visualizing weather and alerts, deployed independently on Vercel

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.1.5 (Web, Actuator) |
| Build Tool | Maven |
| Testing | JUnit 5, Spring Boot Test, JaCoCo |
| Containerization | Docker (multi-stage build, Alpine JRE runtime) |
| CI/CD | Jenkins Pipeline |
| Frontend | HTML, CSS, JavaScript (deployed on Vercel) |

## 🗂️ Project Structure

```
weather-alert-system/
├── src/
│   ├── main/java/com/devops/lab/weatheralert/
│   │   ├── WeatherAlertApplication.java     # Spring Boot entry point
│   │   ├── controller/
│   │   │   └── WeatherController.java       # REST endpoints
│   │   ├── service/
│   │   │   └── WeatherAlertService.java     # Weather simulation & alert logic
│   │   └── model/
│   │       ├── WeatherData.java
│   │       └── AlertResponse.java
│   ├── main/resources/
│   │   └── application.properties           # Server & actuator config
│   └── test/java/.../WeatherAlertServiceTest.java
├── frontend/                                # Static UI (deployed on Vercel)
│   ├── index.html
│   ├── script.js
│   ├── style.css
│   └── vercel.json
├── Dockerfile                               # Multi-stage build (Maven → JRE Alpine)
├── Jenkinsfile                              # CI/CD pipeline definition
├── pom.xml
└── README.md
```

## 🔌 API Reference

Base URL: `http://localhost:8080/api/weather`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/weather` | Service info and available endpoints |
| `GET` | `/api/weather/data/{location}` | Returns simulated weather data for a location |
| `GET` | `/api/weather/check/{location}` | Analyzes weather and returns any triggered alerts |
| `GET` | `/api/weather/health` | Custom service health check |
| `GET` | `/actuator/health` | Spring Boot Actuator health endpoint |

**Example request:**

```bash
curl http://localhost:8080/api/weather/check/Hyderabad
```

**Example response:**

```json
{
  "alertTriggered": true,
  "alerts": [
    "⚠️ HIGH TEMPERATURE ALERT: 37.2°C exceeds threshold of 35.0°C"
  ],
  "weatherData": {
    "location": "Hyderabad",
    "temperature": 37.2,
    "humidity": 62.5,
    "windSpeed": 18.3,
    "condition": "Sunny",
    "timestamp": 1733840000000
  },
  "message": "Weather alerts detected for Hyderabad"
}
```

### Alert thresholds

| Metric | Threshold |
|---|---|
| Temperature | > 35.0 °C |
| Humidity | > 80.0 % |
| Wind Speed | > 50.0 km/h |

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker (optional, for containerized run)

### Run locally

```bash
# Clone the repository
git clone https://github.com/ckarthik77/weather-alert-system.git
cd weather-alert-system

# Build and run
mvn clean package
java -jar target/*.jar
```

The API will be available at `http://localhost:8080/api/weather`.

### Run with Docker

```bash
docker build -t weather-alert-system:latest .
docker run -d -p 8080:8080 --name weather-app weather-alert-system:latest
```

### Run tests

```bash
mvn test
```

Coverage reports are generated via JaCoCo under `target/site/jacoco/`.

## 🔄 CI/CD Pipeline

The included `Jenkinsfile` defines a five-stage pipeline:

1. **Checkout** — pulls the latest code from `main`
2. **Build** — compiles the project with Maven
3. **Test** — runs the JUnit test suite and publishes results
4. **Docker Build** — builds the production image using the multi-stage `Dockerfile`
5. **Deploy** — stops any running container and redeploys the latest image

```
Checkout → Build → Test → Docker Build → Deploy
```

## 🌐 Frontend

A separate static frontend in `frontend/` visualizes weather data and alerts, and is deployed independently to Vercel. It calls the backend's REST API to render live conditions.

## 📄 License

This project is licensed under the MIT License.

## 👤 Author

**Chandika Karthikeya**
[GitHub](https://github.com/ckarthik77) · [LinkedIn](https://linkedin.com/in/karthikeya-chandika)
