# Todo App Automation Project

![Java](https://img.shields.io/badge/Java-23-orange)
![Appium](https://img.shields.io/badge/Appium-2.x-green)
![TestNG](https://img.shields.io/badge/TestNG-Framework-red)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![Allure](https://img.shields.io/badge/Allure-Reporting-purple)

A comprehensive mobile test automation framework built with **Appium**, **TestNG**, and **Java** for automating end-to-end tests on the Todo application. The project follows the **Page Object Model (POM)** design pattern and includes advanced features like Allure reporting, logging, and cross-platform support.

## 🎯 Overview

This project automates test cases for a Todo mobile application, covering critical user workflows:
- ✅ Adding new todo items
- ✏️ Editing existing todo items
- ✓ Marking todos as complete
- 🗑️ Deleting todo items
- 🔄 End-to-end workflows

The framework is designed to be **scalable**, **maintainable**, and **platform-agnostic**, supporting both iOS and Android platforms with minimal configuration changes.

---

## 🛠️ Technology Stack

| Component | Technology |
|-----------|-----------|
| **Mobile Automation** | Appium |
| **Test Framework** | TestNG |
| **Programming Language** | Java |
| **Build Tool** | Maven |
| **Reporting** | Allure |
| **Logging** | Log4j2 |

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)** - Version 23 or higher
  - Download: https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html
  
- **Maven** - Version 3.6.0 or higher
  - Download: https://maven.apache.org/download.cgi
  
- **Appium Server** - Version 2.x
  - Install via npm: `npm install -g appium`

- **Android Setup** (for Android testing):
  - Android SDK installed
  - Android Device or Emulator running
  - USB Debugging enabled (if using physical device)

---

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd TodoAppAutomationProject
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Verify Setup
```bash
mvn -v
java -version
appium --version
```

---

## 🔧 Configuration

### Framework Configuration
Edit `src/main/resources/framework.properties` to customize your setup:

```properties
# Platform settings (android | ios)
platform=android
app.type=native
app.installed=true

# Appium Server
appium.server.url=http://127.0.0.1
appium.server.port=4723

# Android Configuration
android.device.name=Pixel 9
android.app.package=com.todoproject.todoapp
android.app.activity=com.todoproject.todoapp.MainActivity
android.app.path=./src/test/resources/To-Do-App_1.5.apk
android.automation.name=UiAutomator2
android.no.reset=true

# iOS Configuration (if needed)
ios.device.name=iPhone 16
ios.platform.version=17.0
ios.automation.name=XCUITest
```

### Property Overrides
Override properties via Maven command:
```bash
mvn test -Dplatform=ios -Dappium.server.port=4723
```

---

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=E2ETodoTest
```

### Run Specific Test Method
```bash
mvn clean test -Dtest=E2ETodoTest#e2eTodoTest
```

---

## 📁 Project Structure

```
TodoAppAutomationProject/
│
├── src/
│   ├── main/
│   │   ├── java/com/todo/
│   │   │   ├── drivers/                 # Appium driver management
│   │   │   │   ├── AbstractDriver.java  # Base driver class
│   │   │   │   ├── AndroidFactory.java  # Android-specific factory
│   │   │   │   ├── IOSFactory.java      # iOS-specific factory
│   │   │   │   ├── AppiumDriverProvider.java
│   │   │   │   ├── GUIDriver.java       # Main driver interface
│   │   │   │   ├── Platform.java        # Platform enum
│   │   │   │   └── UITest.java          # Annotation for UI tests
│   │   │   │
│   │   │   ├── pages/                   # Page Object Model (POM)
│   │   │   │   └── TodosPage.java       # Todo application page object
│   │   │   │
│   │   │   ├── utils/                   # Utility classes
│   │   │   │   ├── TimeManager.java     # Time & timestamp utilities
│   │   │   │   └── dataReader/
│   │   │   │       └── JsonReader.java  # JSON test data reader
│   │   │   │
│   │   │   ├── server/                  # Appium server management
│   │   │   │   └── AppiumServerManager.java
│   │   │   │
│   │   │   ├── listeners/               # TestNG listeners
│   │   │   │   └── TestNGListeners.java # Custom test event handlers
│   │   │   │
│   │   │   ├── media/                   # Screenshot & media utilities
│   │   │   │   └── ScreenshotsManager.java
│   │   │   │
│   │   │   └── validations/             # Assertion utilities
│   │   │       ├── BaseAssertion.java
│   │   │       ├── SoftAssertion.java   # Non-blocking assertions
│   │   │       └── HardAssertion.java   # Blocking assertions
│   │   │
│   │   └── resources/
│   │       ├── framework.properties     # Framework configuration
│   │       ├── waits.properties         # Wait/timeout configuration
│   │       ├── log4j2.properties        # Logging configuration
│   │       ├── allure.properties        # Allure reporting config
│   │       └── META-INF/
|   |           └── services/
|   |               └── org.testng.ITestNGListener
│   │
│   └── test/
│       ├── java/com/todo/
│       │   ├── BaseTest.java            # Base test class with driver setup
│       │   ├── E2ETodoTest.java         # End-to-end test scenarios
│       │   ├── AddTodoTest.java         # Add todo test cases
│       │   ├── EditTodoTest.java        # Edit todo test cases
│       │   ├── CompleteTodoTest.java    # Complete todo test cases
│       │   └── DeleteTodoTest.java      # Delete todo test cases
│       │
│       └── resources/
│           ├── To-Do-App_1.5.apk        # Android test APK
│           └── test-data/
│               └── todo-data.json       # Test data in JSON format
│
├── test-output/
│   ├── allure-results/                  # Allure report data
│   ├── full-report/                     # Generated Allure HTML report
│   ├── reports/                         # Test execution reports
│   ├── screenshots/                     # Screenshot captures
│   ├── Logs/
│   │   └── logs.log                     # Execution logs
│   └── target/                          # Maven build output
│
├── pom.xml                              # Maven project configuration
└── README.md                            # This file
```

---

## 📊 Test Reports

### Allure Report
Allure generates comprehensive test reports with detailed execution information:

### Open HTML Report
Open `test-output/reports/index.html` in your browser for detailed test execution insights.

---

## 🏗️ Project Architecture

### Design Patterns Used

1. **Page Object Model (POM)**
   - Encapsulates page elements and interactions
   - Improves maintainability and reusability
   - Example: `TodosPage.java`

2. **Factory Pattern**
   - `AndroidFactory.java` and `IOSFactory.java` for platform-specific driver creation
   - Abstracts platform differences

3. **Singleton Pattern**
   - `GUIDriver` maintains a single driver instance per thread

4. **Builder Pattern**
   - Test methods use fluent API for readable test flows
   - Example: `clickAddButton().enterTodoName().clickSaveButton()`

---

## 🎁 Framework Features

✨ **Multi-Platform Support**
- Seamless iOS and Android testing with single codebase
- Platform-specific configurations

🎯 **End-to-End Testing**
- Complete user workflows from creation to deletion
- Status change verification
- Data validation at each step

📸 **Automatic Screenshotting**
- Screenshots captured on test failure
- Stored in `test-output/screenshots/`

📝 **Comprehensive Logging**
- Log4j2 integration for detailed execution logs
- Logs stored in `test-output/Logs/logs.log`

🔄 **Fluent Test API**
- Clean, readable test methods
- Method chaining for intuitive test flows
- Example: `page.clickButton().enterText().verify()`

🛡️ **Assertion Framework**
- Soft assertions (non-blocking)
- Hard assertions (blocking)
- Custom assertion wrappers

📊 **Allure Reporting**
- Rich HTML reports with trends
- Test categorization by feature/epic
- Execution history tracking

👂 **Custom Listeners**
- TestNG event handlers for custom logic
- Automatic report generation
- Test failure handling

---

## 🧪 Test Scenarios Covered

| Test | Description | Coverage |
|------|-------------|----------|
| **E2ETodoTest** | Complete workflow: create, update, complete, delete | BLOCKER |
| **AddTodoTest** | Add new todo items with validation | HIGH |
| **EditTodoTest** | Edit existing todo names and details | HIGH |
| **CompleteTodoTest** | Mark todos as complete/incomplete | MEDIUM |
| **DeleteTodoTest** | Delete todo items with confirmation | HIGH |

---

## 🚨 Troubleshooting

### Android Device Not Detected
```bash
# Check connected devices
adb devices

# Enable USB Debugging on device
# Settings → Developer Options → USB Debugging
```

---

## 📧 Support & Contact

For issues, suggestions, or contributions, please reach out to the project maintainers.
