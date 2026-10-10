# Employee Management System — Phase 2

This is a brief description of  Java desktop application for managing employee records. Built using  Java Swing, JDBC and MySQL.

## Implemented features
- Admin demo login (`admin` / `admin123`)
- Dashboard with employee count
- Adding  employee records
- Viewing  and refreshing records
- Searching by employee name, email, department or position
- Updating selected employee details
- Deleting selected employee with confirmation
- Basic form validation for required fields, email format and salary
- Preparing statements for database operations

## Requirements or things to be used
- JDK 17 or newer
- MySQL Server and MySQL Workbench (or another SQL client)
- Maven
- IntelliJ IDEA, Eclipse or NetBeans

## Setup
1. We are going to use  MySQL Server.
2. Open `database/employee_management.sql` in MySQL Workbench and we  execute it.
3. Open `src/main/java/com/employee/database/DatabaseConnection.java` and set `USER` and `PASSWORD` to your local MySQL credentials.
4. Open this folder as a Maven project in your IDE and allow Maven to download MySQL Connector/J.
5. Run `com.employee.Main`.
6. Then sign in with username `admin` and password `admin123`.



## Project structure
```text
src/main/java/com/employee/
├── Main.java
├── database/DatabaseConnection.java
├── model/Employee.java
├── dao/EmployeeDAO.java
└── gui/
    ├── LoginFrame.java
    └── Dashboard.java
database/employee_management.sql
pom.xml
```

## Author
Muhammad Khaukha— BCA, Graphic Era University
