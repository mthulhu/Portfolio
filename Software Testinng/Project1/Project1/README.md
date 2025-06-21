# Contact, Appointment, and Task Management Project

## Overview

This Java project provides classes and services to manage Contacts, Appointments, and Tasks. It includes features for adding, updating, deleting, and searching for records, as well as sorting and searching.

## Folder Structure

- `src/` - Java source files
- `test/` - JUnit test files

## Features

- **Contact Management**: Add, update, delete, and search contacts by first name, last name, phone, or address. The contact search has been updated to support wildcards. 
- **Appointment Management**: Add, update, delete, and retrieve appointments.
- **Task Management**: Add, update, delete, and retrieve tasks.
- **Sorting & Searching**: Quick sort and binary search implementations for efficient data handling.
- **Unit Tests**: Comprehensive JUnit test coverage for all services and models. Performance tests for contact searching.

## Getting Started

1. **Compile the project**  
   ```
   javac -d bin -cp "lib/*" src/*.java test/*.java
   ```

3. **Run the tests**  
   ```
   java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path bin --scan-class-path
   ```

## Dependencies (included in /lib)

- JUnit 4.13.2
- JUnit Jupiter 5.13.1
- JUnit Platform Console Standalone 1.13.1

## Author

Matt Krueger

