package com.employee.model;

import java.math.BigDecimal;

public class Employee {
    private int id;
    private String name, email, phone, department, position;
    private BigDecimal salary;

    public Employee(int id, String name, String email, String phone, String department, String position, BigDecimal salary) {
        this.id = id; this.name = name; this.email = email; this.phone = phone;
        this.department = department; this.position = position; this.salary = salary;
    }
    public Employee(String name, String email, String phone, String department, String position, BigDecimal salary) {
        this(0, name, email, phone, department, position, salary);
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
}
