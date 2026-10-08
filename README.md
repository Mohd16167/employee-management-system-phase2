# Employee Management System — Phase 2

A Java desktop application for managing employee records. Built as a BCA project using Java Swing, JDBC and MySQL.

## Implemented features
- Admin demo login (`admin` / `admin123`)
- Dashboard with employee count
- Add employee records
- View and refresh records
- Search by employee name, email, department or position
- Update selected employee details
- Delete selected employee with confirmation
- Basic form validation for required fields, email format and salary
- Prepared statements for database operations

## Requirements
- JDK 17 or newer
- MySQL Server and MySQL Workbench (or another SQL client)
- Maven
- IntelliJ IDEA, Eclipse or NetBeans

## Setup
1. Start MySQL Server.
2. Open `database/employee_management.sql` in MySQL Workbench and execute it.
3. Open `src/main/java/com/employee/database/DatabaseConnection.java` and set `USER` and `PASSWORD` to your local MySQL credentials.
4. Open this folder as a Maven project in your IDE and allow Maven to download MySQL Connector/J.
5. Run `com.employee.Main`.
6. Sign in with username `admin` and password `admin123`.

## Important
The login is a simple demonstration login for a student project, not production-grade authentication. Do not use real employee or salary data in a public repository. The database password is configured locally and should never be committed if it is a real secret.

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
Mohez Jr — BCA, Graphic Era University
