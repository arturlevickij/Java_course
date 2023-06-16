# FlightRadar api
A Java application that allows that allows you to store information about the current location of the aircraft (its flight, route, speed, airline, previous flights, total flight, registration information)
# Functions
Create, update and delete aircrafts.
Read from CSV files
Write to CSV files
Technologies Used:
Java
Spring Framework
Maven
Installation and Usage

To run this project locally, follow these steps:
Clone the repository: git clone https://github.com/arturlevickij/Java_course/tree/course_project
Open project in your IDE
Build project with command : mvn clean install
Run the application with command : mvn spring-boot: run
The application will start running on http://localhost:8080.
Use GET/aircrafts to retrieve a list of all entities
Use GET/aircrafts/{id} to retrieve a aircrafts by ID.
Use POST/aircrafts to create a flood aircrafts(the body requires).
Use PUT/aircrafts/{id} to update an existing aircrafts.
Use DELETE/aircrafts/{id} to delete aircrafts by ID.

DateStorage
The application stores data in CSV files located in the src/main/resources/aircrafts.
