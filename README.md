# Hello World Java-Kotlin Mobile App

A basic Android mobile application that demonstrates Java-Kotlin interoperability. This app shows how Java and Kotlin code can work together seamlessly in an Android project.

## Features

- **Java-Kotlin Interoperability**: Demonstrates calling Java code from Kotlin and vice versa
- **Simple UI**: Clean Material Design interface with buttons to trigger different language features
- **Modern Android Development**: Uses AndroidX, Material Components, and ConstraintLayout
- **Gradle Build System**: Configured with both Java and Kotlin support

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/helloworld/java/
│   │   └── HelloWorldJava.java          # Java helper class
│   ├── kotlin/com/example/helloworld/
│   │   └── MainActivity.kt              # Main activity in Kotlin
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml        # UI layout
│   │   ├── values/
│   │   │   ├── colors.xml               # Color definitions
│   │   │   ├── strings.xml              # String resources
│   │   │   └── themes.xml               # App theme
│   │   └── xml/
│   │       ├── backup_rules.xml         # Backup configuration
│   │       └── data_extraction_rules.xml # Data extraction rules
│   └── AndroidManifest.xml              # App manifest
├── build.gradle                         # App-level build configuration
└── proguard-rules.pro                   # ProGuard rules

build.gradle                             # Project-level build configuration
settings.gradle                          # Project settings
gradle.properties                        # Gradle properties
gradlew                                  # Gradle wrapper script
```

## Java-Kotlin Interoperability

This project demonstrates several aspects of Java-Kotlin interoperability:

### Java Class (`HelloWorldJava.java`)
```java
public class HelloWorldJava {
    public String getHelloMessage() {
        return "Hello World from Java! ☕";
    }
    
    public String getGreeting(String name) {
        return "Hello " + name + " from Java!";
    }
    
    public int addNumbers(int a, int b) {
        return a + b;
    }
    
    public static String getStaticMessage() {
        return "Static message from Java!";
    }
}
```

### Kotlin Activity (`MainActivity.kt`)
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Call Java class from Kotlin
        val javaHelper = HelloWorldJava()
        val message = javaHelper.getHelloMessage()
    }
}
```

## Prerequisites

- Android Studio (latest version recommended)
- Java Development Kit (JDK) 8 or higher
- Android SDK (API level 24 or higher)

## Building and Running

### Option 1: Using Android Studio
1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Connect an Android device or start an emulator
4. Click the "Run" button (green play icon)

### Option 2: Using Command Line
1. Make sure you have the Android SDK installed and `ANDROID_HOME` environment variable set
2. Run the following commands:

```bash
# Make gradlew executable (if not already)
chmod +x gradlew

# Build the project
./gradlew build

# Install and run on connected device/emulator
./gradlew installDebug
```

### Option 3: Using Gradle Wrapper
```bash
# Clean and build
./gradlew clean build

# Run tests
./gradlew test

# Generate debug APK
./gradlew assembleDebug
```

## Key Features Demonstrated

1. **Language Interoperability**: Java and Kotlin code working together
2. **Modern Android UI**: Material Design components and ConstraintLayout
3. **Resource Management**: Proper use of Android resources (strings, colors, themes)
4. **Build Configuration**: Gradle setup for mixed Java-Kotlin projects
5. **Activity Lifecycle**: Basic Android activity implementation

## Dependencies

- **AndroidX Core KTX**: Kotlin extensions for Android
- **AndroidX AppCompat**: Backward compatibility library
- **Material Components**: Modern Material Design components
- **ConstraintLayout**: Flexible layout system
- **JUnit**: Unit testing framework
- **Espresso**: UI testing framework

## Configuration

The project is configured with:
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Kotlin Version**: 1.8.20
- **Gradle Version**: 8.0

## Troubleshooting

### Common Issues

1. **Gradle Sync Failed**: Make sure you have the correct JDK version installed
2. **Build Errors**: Clean and rebuild the project (`./gradlew clean build`)
3. **Device Not Found**: Ensure USB debugging is enabled on your device
4. **SDK Issues**: Verify Android SDK installation and environment variables

### Environment Setup

Make sure these environment variables are set:
```bash
export ANDROID_HOME=/path/to/android/sdk
export JAVA_HOME=/path/to/jdk
```

## Contributing

Feel free to extend this project by:
- Adding more Java-Kotlin interoperability examples
- Implementing additional UI features
- Adding unit tests
- Improving the design and user experience

## License

This project is open source and available under the MIT License. 