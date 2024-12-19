# Coding Challenge Project - README

## Project Overview

This project is a solution to the coding challenge task. It demonstrates a combination of various Android development tools, libraries, and patterns to build a robust application. The project utilizes libraries like Dagger Hilt for dependency injection, Retrofit for networking, Glide for image loading, and RxJava for reactive programming.

## Tools and Libraries Used

### 1. **IDE & SDKs:**
- **IDE:** Android Studio Ladybug | 2024.2.1 Patch 2
- **Java SDK:** JetBrains Runtime 21.0.3

### 2. **Plugins & Dependencies:**
- **Dagger Hilt:** Version 2.49 - Dependency Injection for simplifying the DI process.
- **3rd Party Libraries:**
  - **Glide**: Image loading library for efficient and easy image loading in Android.
  - **Gson**: A library for serializing and deserializing Java objects to/from JSON.
  - **Retrofit**: A type-safe HTTP client for Android and Java.
  - **OkHttp Logging Interceptor**: Logs HTTP request and response data for debugging purposes.
  - **Gson Converter Factory**: Used for Retrofit to convert JSON into Java objects using Gson.
  - **RxJava**: A library for composing asynchronous and event-based programs using observable sequences.
  - **RxAndroid**: Provides Android-specific bindings for RxJava to interact with Android's UI thread.

### 3. **Important Native Libraries:**
- **ViewModel**: For managing UI-related data lifecycle-consciously.
- **LiveData**: A lifecycle-aware data holder for managing UI-related data that can be observed.

## Project Setup & Instructions

Follow the steps below to clone and set up this project locally.

### Step 1: Clone the Git Repository

Start by cloning the repository to your local machine:

```bash
git clone https://github.com/theRockAkash/Coding_Challenge.git
```


### Step 2: Open the Project in Android Studio

1. Launch **Android Studio**.
2. Select **Open** and navigate to the folder where the project was cloned.
3. Click **OK** to open the project.

### Step 3: Set Up Dependencies

Android Studio will automatically detect the `build.gradle` files and sync the necessary dependencies. However, make sure that you have the following installed on your system to avoid potential issues:

- **Android SDK** with **Android API Level 21** or higher.
- **Java SDK**: Ensure **JetBrains Runtime 21.0.3** is set up (this is the default Java SDK used in the project).

### Step 4: Build the Project

Once the project is loaded, you can **build the project** by selecting **Build > Make Project** from the top menu or using the shortcut `Ctrl + F9` (Windows/Linux) or `Cmd + F9` (Mac).

### Step 5: Running the Application

You can run the project on an **emulator** or a **physical device**. Make sure that you have:

- **USB Debugging enabled** on your device (for physical devices).
- A **configured emulator** (for virtual devices) in Android Studio.

To run the project:

1. Click on the **Run** button in Android Studio (the green triangle) or use the shortcut `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac).
2. Select the device (emulator or connected physical device) and click **OK**.


### Step 6: Update Dependencies

If you need to update the dependencies:

1. Open the `build.gradle` files (both project-level and app-level).
2. Modify the versions of the libraries you want to update.
3. After making the necessary changes, click **Sync Now** in the top right corner of Android Studio.

## Folder Structure

Here is an overview of the folder structure for the project:

```
├── app/                              # Application module
│   ├── build.gradle                  # App-level Gradle configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/
│   │   │   │   │   ├── example/
│   │   │   │   │   │   ├── codingchallange/  # Main project package
│   │   │   │   │   │   │   ├── ui/            # UI-related classes (e.g., Activities, Fragments, ViewModels)
│   │   │   │   │   │   │   ├── data/          # Data-related classes (e.g., Models, ApiService)
│   │   │   │   │   │   │   ├── di/            # Dependency Injection related classes (e.g., NetworkModule, AppModule)
│   │   │   │   │   │   │   └── utils/          # Utility classes (e.g., ConnectivityManager, SharedPreferencesManager)
│   │   │   ├── res/
│   │   │   │   ├── layout/             # XML layout files
│   │   │   │   ├── values/             # Colors, Strings, Styles
│   │   │   └── AndroidManifest.xml     # Manifest file
├── build.gradle                       # Project-level Gradle configuration
├── settings.gradle                    # Gradle settings file
└── gradle/                            # Gradle wrapper files

```

### Key Folders:
- `di/`: Contains Dagger Hilt-related files.
- `data/`: Contains model classes and ApiService 
- `ui/`: Contains the UI-related components like activities, adapters, and view models.

## Troubleshooting

- **Dependencies not syncing properly?**
  - Ensure that you have a stable internet connection and that your Gradle wrapper is up-to-date. If syncing issues persist, try invalidating caches and restarting Android Studio: **File > Invalidate Caches / Restart**.
  
- **Emulator not launching?**
  - Make sure your emulator is running the correct Android version and that your system has enough resources to run the emulator.

- **Build errors related to Dagger Hilt?**
  - Ensure that you've correctly annotated all necessary classes with `@Inject` and have set up the `@HiltAndroidApp` annotation in your `Application` class.

## Conclusion

This README provides a comprehensive guide to setting up and running the project. By following these instructions, you should be able to clone the repository, set up the development environment, and start working with the codebase quickly. Make sure to explore the important libraries and patterns used in the project to enhance your knowledge of Android development best practices.

