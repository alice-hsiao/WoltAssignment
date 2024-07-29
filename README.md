# Flix-Assignment


## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)

## Introduction
Restaurant Fetching:
When the app is opened, it retrieves restaurant data from the Wolt server every 10 seconds.
Fetching is paused when the app is in the background and resumes when the app returns to the foreground.

Like Feature:
Each restaurant card features a heart icon in the top right corner that animates slightly when clicked.
The state of liked restaurants is preserved even after new data is fetched from the Wolt server.

Design and Testing:
A custom design system is implemented across the app.
The app is covered with unit tests to ensure reliability and functionality.

Error Handling:
When the API throws an exception, the app displays the error message in a snackbar.

## Features
- Custom Design System
- Present nearby restaurants in a grid view 
- Handle network errors by showing error in a snackbar

## Architecture
This project follows the MVVM (Model-View-ViewModel) architecture pattern, which helps to separate the concerns of the UI, business logic, and data access. The key components are:

- **Model:** Represented by the data package, responsible for managing the data, such as fetching data from a remote API.
- **Domain Layer:** Handles business logic without holding any platfomr-specific references. Has its own domain objects, sperating from DTO (Data Transfer Object) in the data layer. Can be unit-tested.
- **View:** The UI layer that displays data and forwards user interactions to the ViewModel.
- **ViewModel:** Acts as a bridge between the Model and the View. It holds and processes the data to be displayed by the View, using StateFlow.

### Project Structure
├── core (shares common stuff across different features (modules). we only have one module now tho.)

├── data

- │ ├── dto

- │ ├── repository

├── di

├── domain

- │ ├── model

├── ui


## Tech Stack
- **Kotlin:** Main programming language.
- **Jetpack Compose:** For building declarative UI in Android.
- **Coroutines:** For managing background threads with simplified code.
- **Retrofit:** For network operations.
- **StateFlow:** For observable data streams.
- **Coil:** For loading images.
- **kotest, turbine:** For unit testing.
