# 🌦️ WeatherMVI - A Clean Architecture Weather App

A modern, highly-optimized Android weather application built using **Kotlin**, **Jetpack Compose**, and structured around the principles of **Clean Architecture** and **MVI (Model-View-Intent)**. The app provides real-time weather data and robust location search capabilities.

## ✨ Key Features

* **Current Location Tracking:** Automatically determines the user's location via the device's location services (`LocationTracker` using Google Play Services) and uses Reverse Geocoding via LocationIQ to identify the current city name.
* **City Search & Autocomplete:** Search for weather in any city globally with intelligent autocomplete suggestions from the LocationIQ API.
* **Offline Caching & Persistence:** Utilizes **Room** to store location (`LocationInfoEntity`) and weather data, ensuring data availability and a seamless experience even when offline.
* **Modern UI:** A clean, responsive, and minimalist user interface developed entirely with **Jetpack Compose**.
* **Optimized Performance:** Uses Kotlin Coroutines and Flow for efficient, non-blocking asynchronous operations.

---

## 🛠️ Tech Stack & Architecture

This project strictly adheres to **Clean Architecture** principles, separating concerns into Domain, Data, and Presentation layers, and employs the **MVI** pattern for predictable state management.

### Core Technologies

| Technology | Purpose | Dependency Reference |
| :--- | :--- | :--- |
| **Kotlin** | Primary language for Android development. | - |
| **Jetpack Compose** | Declarative UI toolkit. | `androidx.compose.bom`, `androidx.material3` |
| **Hilt** | Dependency Injection framework for simplified scoping and lifecycle management. | `com.google.dagger:hilt-android`, `androidx.hilt:hilt-navigation-compose` |
| **Kotlin Flow** | Asynchronous data streams for reactive data handling in MVI and Repository layers. | - |
| **Retrofit & OkHttp** | Type-safe HTTP client for communicating with the Weather and LocationIQ APIs. | `com.squareup.retrofit2:retrofit`, `com.squareup.okhttp3:logging-interceptor` |
| **Room** | Local persistence of critical data (Location and Weather Cache). | `androidx.room:room-ktx`, `androidx.room:room-runtime` |
| **Location Services** | Accessing the device's precise location for weather data retrieval. | `com.google.android.gms:play-services-location` |

### Data Flow Overview

The application's data flow is managed as a stream of data/errors wrapped in a `Result<T, WeatherError>` object.

* **Presentation (MVI):** Compose UI sends **Events** $\rightarrow$ `ViewModel` consumes Events and exposes **State** (`Flow`).
* **Domain (Use Cases):** Holds business logic and coordinates data requests.
* **Data (Repository Impls):** Determines data source (Network or Local).
    * **Network:** `WeatherApi` / `LocationIQApi` fetch data.
    * **Local:** Data cached using `Room`.
    * **Caching Strategy:** Fetches local cached data first, displays it with a loading indicator, then attempts to fetch fresh data from the remote source if internet is available, and updates the cache.

---

## ⚙️ Getting Started

### Prerequisites

* Android Studio IDE
* A physical device or emulator running **API 24+** (`minSdk 24`)

### API Key Setup

This project uses the **LocationIQ API** for geocoding services. You must obtain an API key and configure it locally for the build process.

1.  **Get a Key:** Sign up for an API key on the LocationIQ website.
2.  **Create `local.properties`:** In your project's root directory, create a file named `local.properties` (this file is excluded from Git).
3.  **Add the Key:** Insert your API key into the file using the exact variable name:

    ```properties
    LOCATIONIQ_API_KEY="YOUR_LOCATIONIQ_API_KEY_HERE"
    ```

4.  **Gradle Integration:** The `build.gradle.kts` file reads this key and exposes it to the application as a `BuildConfig` field (`BuildConfig.LOCATIONIQ_API_KEY`), which is then used by `LocationIQApi.kt`.

### Installation

1.  Clone the repository:
    ```bash
    git clone [Placeholder: Insert your GitHub Repo URL here]
    ```
2.  Open the project in Android Studio.
3.  Ensure your `local.properties` file is set up correctly (see above).
4.  Build and run the application.

---

## 📸 Screenshots (Placeholder)

*(Please replace this section with high-quality screenshots/GIFs of your Compose UI to showcase the design.)*


| [screenshots/preview1.jpg](screenshots/preview1.jpg) | [screenshots/preview2.jpg](screenshots/preview1.jpg) |
| :---: | :---: |
---

## 🤝 Contribution

Contributions are highly encouraged! Please feel free to open an issue to discuss features or submit a pull request with improvements.

---

## 📄 License

[Placeholder: Insert Project License here (e.g., MIT, Apache 2.0)]
