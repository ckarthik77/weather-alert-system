// API Configuration
// Set USE_MOCK_DATA to true when deployed to Vercel (no backend available)
// Set to false when running locally with Spring Boot backend
const USE_MOCK_DATA = true; // CHANGE THIS TO true FOR VERCEL DEPLOYMENT
const API_BASE_URL = 'http://localhost:8080/api/weather';

// Format JSON response for display
function formatJSON(obj) {
    return JSON.stringify(obj, null, 2);
}

// Display response in the response box
function displayResponse(data, type = 'success') {
    const responseBox = document.getElementById('response');
    responseBox.className = `response-box response-${type}`;
    
    if (typeof data === 'object') {
        responseBox.innerHTML = `<pre>${formatJSON(data)}</pre>`;
    } else {
        responseBox.innerHTML = `<pre>${data}</pre>`;
    }
}

// Mock data generator
function generateMockWeatherData(location) {
    const temp = 15 + Math.random() * 25;
    const humidity = 30 + Math.random() * 60;
    const windSpeed = 5 + Math.random() * 60;
    const conditions = ["Sunny", "Cloudy", "Rainy", "Stormy", "Windy", "Clear"];
    
    const alerts = [];
    let alertTriggered = false;
    
    if (temp > 35) {
        alerts.push(`⚠️ HIGH TEMPERATURE ALERT: ${temp.toFixed(1)}°C exceeds threshold of 35.0°C`);
        alertTriggered = true;
    }
    if (humidity > 80) {
        alerts.push(`⚠️ HIGH HUMIDITY ALERT: ${humidity.toFixed(1)}% exceeds threshold of 80.0%`);
        alertTriggered = true;
    }
    if (windSpeed > 50) {
        alerts.push(`⚠️ HIGH WIND ALERT: ${windSpeed.toFixed(1)} km/h exceeds threshold of 50.0 km/h`);
        alertTriggered = true;
    }
    
    return {
        alertTriggered,
        alerts,
        weatherData: {
            location,
            temperature: parseFloat(temp.toFixed(1)),
            humidity: parseFloat(humidity.toFixed(1)),
            windSpeed: parseFloat(windSpeed.toFixed(1)),
            condition: conditions[Math.floor(Math.random() * conditions.length)],
            timestamp: Date.now()
        },
        message: alertTriggered ? `Weather alerts detected for ${location}` : `Weather conditions normal for ${location}`
    };
}

// Check Weather and Alerts
async function checkWeather() {
    const location = document.getElementById('location').value.trim();
    
    if (!location) {
        displayResponse('Please enter a location', 'error');
        return;
    }
    
    displayResponse('Loading...', 'info');
    
    if (USE_MOCK_DATA) {
        // Use mock data for demo
        setTimeout(() => {
            const data = generateMockWeatherData(location);
            displayResponse(data, 'success');
        }, 500);
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/check/${encodeURIComponent(location)}`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        displayResponse(data, 'success');
    } catch (error) {
        displayResponse({
            error: 'Failed to fetch weather data',
            message: error.message,
            note: 'Make sure the Spring Boot application is running on http://localhost:8080',
            solution: 'Run: mvn spring-boot:run'
        }, 'error');
    }
}

// Get Weather Data Only
async function getWeatherData() {
    const location = document.getElementById('location').value.trim();
    
    if (!location) {
        displayResponse('Please enter a location', 'error');
        return;
    }
    
    displayResponse('Loading...', 'info');
    
    if (USE_MOCK_DATA) {
        // Use mock data for demo
        setTimeout(() => {
            const fullData = generateMockWeatherData(location);
            displayResponse(fullData.weatherData, 'success');
        }, 500);
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/data/${encodeURIComponent(location)}`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        displayResponse(data, 'success');
    } catch (error) {
        displayResponse({
            error: 'Failed to fetch weather data',
            message: error.message,
            note: 'Make sure the Spring Boot application is running on http://localhost:8080',
            solution: 'Run: mvn spring-boot:run'
        }, 'error');
    }
}

// Check API Health
async function checkHealth() {
    displayResponse('Checking API health...', 'info');
    
    if (USE_MOCK_DATA) {
        // Use mock data for demo
        setTimeout(() => {
            displayResponse({
                status: "UP",
                service: "weather-alert-system",
                healthy: true,
                timestamp: Date.now(),
                note: "Demo mode - using mock data"
            }, 'success');
        }, 500);
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/health`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        displayResponse(data, 'success');
    } catch (error) {
        displayResponse({
            error: 'API is not reachable',
            message: error.message,
            status: 'DOWN',
            note: 'The Spring Boot application is not running',
            solution: 'Start the application with: mvn spring-boot:run'
        }, 'error');
    }
}

// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Add Enter key support for location input
document.addEventListener('DOMContentLoaded', () => {
    const locationInput = document.getElementById('location');
    if (locationInput) {
        locationInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                checkWeather();
            }
        });
    }
});