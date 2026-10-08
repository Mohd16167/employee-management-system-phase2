CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

CREATE TABLE IF NOT EXISTS employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(25),
    department VARCHAR(80),
    position VARCHAR(80),
    salary DECIMAL(12,2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO employees (name,email,phone,department,position,salary)
SELECT 'Amina Nanyonga','amina@example.com','0700000001','IT','Junior Developer',35000.00
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email='amina@example.com');
INSERT INTO employees (name,email,phone,department,position,salary)
SELECT 'Daniel Okello','daniel@example.com','0700000002','Human Resources','HR Assistant',28000.00
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email='daniel@example.com');
