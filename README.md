# Mood Tracker Application
This is a console-based Mood Tracker application developed as the final project for the "Object-Oriented Programming in Java" course by IBM on Coursera. This application allows users to track their moods by creating, storing, retrieving, editing, and deleting mood entries.

## Table of Contents
* [Features](#features)

* [Technologies Used](#technologies-used)

* [Project Structure](#project-structure)

* [Usage](#usage)

* [Learning Objectives](#learning-objectives)

* [Acknowledgements](#acknowledgements)

## Features
* **Create Mood Entries:** Users can add new mood entries with attributes like name, date, time, and notes.

* **Default Date and Time:** New mood entries default to the current date and `LocalTime.MIDNIGHT` if not specified.

* **Overloaded Constructors:** The `Mood` class provides multiple constructors for flexible object creation.

* **Duplicate Mood Validation:** Prevents adding a mood if an entry with the same date and time already exists.

* **Delete Moods:**

    * Delete all moods recorded for a specific date.

    * Delete a specific mood based on name, date, and time.

* **Edit Moods:** Modify the notes for an existing mood entry (identified by name, date, and time).

* **Search Moods:**
    
    * Retrieve all moods for a specific date.

    * Retrieve a specific mood based on name, date, and time.

* **Console Interface:** All interactions are handled through a command-line interface with a menu-driven system.

* **Exception Handling:** Includes a custom InvalidMoodException for validation scenarios.

* **File I/O (Planned/To be Implemented based on project steps):** The application design includes a placeholder for writing moods to a file.

## Technologies Used
* Java Development Kit (JDK) 24

* Core Java concepts (Classes, Objects, Encapsulation, Polymorphism)

* Java Collections (specifically `ArrayList` for storing `Mood` objects)

* `java.time` package for Date and Time API (`LocalDate`, `LocalTime`, `DateTimeFormatter`)

* `java.util.Scanner` for console input.

## Project Structure
The project follows a standard Java project structure:
```bash
    final_proj/
    ├── src/
    │   ├── Mood.java
    │   ├── InvalidMoodException.java
    │   └── MoodTracker.java
```

## Usage
Once the application is running, you will be presented with a menu of options:
```
    Press 'a' to add mood
    'd' to delete mood(s)
    'e' to edit mood
    's' to search for moods
    'M' to get all moods
    'w' to write the moods to a file
    Type 'Exit' to exit
```
Follow the on-screen prompts to interact with the Mood Tracker.

## Learning Objectives
This project helped in applying the following concepts learned in the course:

* Creating classes and objects using Object-Oriented Programming (OOP) concepts like encapsulation and polymorphism.

* Using collections (specifically `ArrayList`) to store objects in memory.

* Creating, storing, retrieving, and manipulating Date and Time objects using the `java.time` API.

* Handling File Input/Output (I/O) (as indicated in project objectives and menu options).

## Acknowledgements
This project was completed as part of the "Object-Oriented Programming in Java" course offered by IBM on Coursera.
