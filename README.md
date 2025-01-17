﻿# TrueCaller_assignment for Technical Operations Intern

# TrueCaller OAuth Integration

This project demonstrates the integration of **TrueCaller SDK 3.0.0** for authentication and fetching user details in an Android application using Kotlin.

## Features
- **1-Click Registration**: Simplifies user onboarding by utilizing TrueCaller's OAuth flow.
- **User Details Fetching**: Retrieves the authenticated user's name, phone number, and other profile details from TrueCaller's backend.
- **Seamless Navigation**: Displays user details on a dedicated screen after successful authentication.

## Key Functionalities
1. **TrueCaller OAuth Flow**:
   - Implements the OAuth flow using TrueCaller's SDK.
   - Exchanges the authorization code for an access token.

2. **User Profile Retrieval**:
   - Fetches user details such as first name, last name, and phone number using the access token.

3. **Modern Android Architecture**:
   - **Jetpack Compose**: For building the UI.
   - **MVVM Architecture**: For managing UI and business logic with `ViewModel`.

## Integration Steps
1. Add TrueCaller SDK 3.0.0 dependencies to your project.
2. Initialize the SDK using the provided client ID in `MainActivity`.
3. Handle user authentication via the `TrueCallerApi` service using Retrofit.
4. Display user details on the `InfoDisplayScreen` after successful registration.

## Usage
- Clone the repository.
- Replace `YOUR_CLIENT_ID` in `res/values/strings.xml` with your TrueCaller client ID.
- Build and run the application on an Android device or emulator.
- Follow the 1-click registration flow to fetch and view user details.

## Dependencies
- **TrueCaller SDK 3.0.0**
- **Jetpack Compose**
- **Retrofit**
- **Kotlin Coroutines**

---
## Screenshots
<p align="center">
  <img src="https://i.postimg.cc/j24z5dgf/home.png" width="150"> 
  <img src="https://i.postimg.cc/zDCwp0zf/manual.png" width="150"> 
  <img src="https://i.postimg.cc/C1DG34bm/Manual-Success.png" width="150"> 
  <img src="https://i.postimg.cc/8PTRYMcm/1click-Pop-Up-Test-Num.png" width="150"> 
  <img src="https://i.postimg.cc/6qXrtG0w/Trucaller-Succes.png" width="150">
</p>

This project is designed as a template for integrating TrueCaller's OAuth functionality in Android applications.
