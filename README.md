# Flight Reservation System (Console – Java)




## Overview

This is a simple console-based Flight Reservation System implemented in Java.  
It allows a user to:

- Search for flights by **destination** and **date**
- Book seats on a flight
- View their reservations

The implementation focuses on clean code, basic domain modeling, and testable business logic.

## Tech Stack

- Java 17
- JUnit 5 for unit testing

## How to Run

1. Clone the repository.
2. Compile the project:

   ```bash
   javac *.java
   ```
3. Run the console application:

   ```bash
   java ConsoleApp
   ```
   

### Testing (Manual)

For simplicity and portability, this project uses a manual test runner:

- `FlightServiceManualTest.java`

This class validates:
- Flight searching
- Successful booking
- Overbooking prevention
- Reservation retrieval

JUnit can be easily integrated later using Maven or Gradle if needed.
