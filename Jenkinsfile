pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }
    
    environment {
        DOCKER_IMAGE = 'weather-alert-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out code...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo '🔨 Building application...'
                sh 'mvn clean compile'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo '🧪 Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '📦 Packaging application...'
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }
        
        stage('Deploy to Local') {
            steps {
                echo '🚀 Deploying application...'
                sh """
                    docker stop weather-alert-system || true
                    docker rm weather-alert-system || true
                    
                    docker run -d --name weather-alert-system \
                        -p 8080:8080 \
                        --restart unless-stopped \
                        ${DOCKER_IMAGE}:latest
                    
                    sleep 10
                    curl -f http://localhost:8080/actuator/health
                """
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
```

6. **Save:** Ctrl+S

---

### **FILE 12: .gitignore**

**Path:** Project Root (`weather-alert-system/.gitignore`)

1. Right-click on **project root**
2. **New → File**
3. **File name:** `.gitignore`
4. Click **Finish**
5. **Add this content:**
```
target/
.settings/
.classpath
.project
*.iml
.idea/
.DS_Store
*.log
```

6. **Save:** Ctrl+S

---

## **PHASE 3: EXECUTION & TESTING**

### **STEP 1: Clean and Build Project**

1. Right-click on **project** → **Run As → Maven clean**
2. Wait for completion (check Console)
3. Right-click on **project** → **Run As → Maven install**
4. Should see: **BUILD SUCCESS**

---

### **STEP 2: Run Application**

1. In Package Explorer, navigate to:
   `src/main/java → com.devops.lab.weatheralert → WeatherAlertApplication.java`
2. Right-click on **WeatherAlertApplication.java**
3. **Run As → Java Application**
4. **Check Console** - you should see:
```
   🌤️  Weather Alert System Started Successfully!
   📊 Health Check: http://localhost:8080/actuator/health
   🔗 API Endpoint: http://localhost:8080/api/weather
```

---

### **STEP 3: Test Application in Browser**

Open these URLs in your browser:

**1. Health Check:**
```
http://localhost:8080/actuator/health
```
Expected: `{"status":"UP"}`

**2. API Welcome:**
```
http://localhost:8080/api/weather
```
Expected: JSON with service info

**3. Weather Data:**
```
http://localhost:8080/api/weather/data/Mumbai
```
Expected: Weather data JSON

**4. Weather Alerts:**
```
http://localhost:8080/api/weather/check/Delhi
```
Expected: Alert response with weather analysis

---

### **STEP 4: Run Unit Tests**

1. Right-click on **project**
2. **Run As → Maven test**
3. **Check Console** - should see:
```
   Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
   BUILD SUCCESS