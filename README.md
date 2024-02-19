# PetFinderApp


<img src="https://github.com/IuliuCristianDumitrache/PetFinderApp/blob/main/petappfull.gif" width="400" height="790">


## This repository contains a demonstration of a pet adoption application built from scratch, focusing on implementing best practices and clean code principles. The application interacts with the Petfinder API to fetch data about adoptable pets and provides features to browse, filter, and view details about these pets.

## Features
- Fetches data from the Petfinder API.
- Displays a list of adoptable pets.
- Provides a detailed view for each pet, including information such as name, breed, size, gender, status, and distance.
- Implements clean code practices for maintainability and readability.
- Demonstrates error handling strategies.
- Includes unit tests for reliability and robustness.

## Additional Functionality
- Enables filtering of search results based on various criteria.
- Allows users to search for animals nearby.
- Provides the ability to add pets to a list of favorites.

## Third-party Libraries
1. **Retrofit2:**
   - *Implementation:* com.squareup.retrofit2:retrofit:2.9.0
     - Retrofit is a type-safe HTTP client for Android and Java used to make API calls in a structured and organized manner.
   - *Implementation:* com.squareup.retrofit2:converter-gson:2.9.0
     - Gson converter for Retrofit, used for converting JSON response from API calls to Java/Kotlin objects.
   
2. **OkHttp Logging interceptor:**
   - *Implementation:* com.squareup.okhttp3:logging-interceptor:4.9.1
     - OkHttp is an HTTP client for Java and Kotlin. The logging interceptor is used for logging HTTP request and response data for debugging purposes.
   
3. **Dagger Hilt:**
   - *Implementation:* com.google.dagger:hilt-android:2.44
     - Dagger Hilt is a dependency injection library for Android. It simplifies the process of managing dependencies and facilitates modularization.
   - *Implementation:* androidx.hilt:hilt-navigation-compose:1.1.0
     - Hilt Navigation Compose provides integration between Jetpack Navigation and Hilt for dependency injection in Jetpack Compose-based applications.
   - *Kapt:* com.google.dagger:hilt-android-compiler:2.44, androidx.hilt:hilt-compiler:1.1.0
     - Annotation processors for Dagger Hilt to generate code for dependency injection.
   - *Implementation:* androidx.hilt:hilt-work:1.1.0
     - Hilt Work provides support for dependency injection in Android WorkManager classes.
   
4. **ViewModel:**
   - *Implementation:* androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
     - ViewModel component of the Android Architecture Components used for managing UI-related data in a lifecycle-conscious way.
   - *Implementation:* androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0, androidx.lifecycle:lifecycle-runtime-compose:2.7.0
     - ViewModel utilities for integration with Jetpack Compose.
  
   
5. **Room:**
   - *Kapt:* androidx.room:room-compiler:2.4.2
     - Annotation processor for Room database to generate boilerplate code.
   - *Implementation:* androidx.room:room-ktx:2.4.2, androidx.room:room-runtime:2.4.2
     - Room persistence library for database management in Android applications.
   
   
6. **Coil:**
   - *Implementation:* io.coil-kt:coil-compose:2.4.0
     - Image loading library for Android applications, specifically designed for Jetpack Compose.
  
    
7. **Constraint Layout:**
    - *Implementation:* androidx.constraintlayout:constraintlayout-compose:1.0.1
      - ConstraintLayout library for building complex UI layouts in Jetpack Compose.
    
8. **Testing:**
    - *TestImplementation:* io.mockk:mockk:1.13.9
      - Mocking library for Kotlin used in unit testing to simulate behavior of objects and dependencies.
     
9. **Paging:**
  - *Implementation:* androidx.paging:paging-compose:3.2.1
  - *Implementation:* androidx.paging:paging-runtime-ktx:3.2.1
    - Paging is a technique used in software development, particularly in mobile and web applications, to efficiently load and display large datasets in smaller, manageable chunks or pages.
    
10. **Accompanist:**
 - *Implementation:* com.google.accompanist:accompanist-permissions:0.34.0
  - Accompanist library for handling permissions in Jetpack Compose.
