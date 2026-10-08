package com.employee.dao;

import com.employee.database.DatabaseConnection;
import com.employee.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Object: keeps SQL operations separate from the user interface. */
public class EmployeeDAO {
    public List<Employee> findAll(String search) throws SQLException {
        String sql = "SELECT id,name,email,phone,department,position,salary FROM employees " +
                "WHERE name LIKE ? OR email LIKE ? OR department LIKE ? OR position LIKE ? ORDER BY id DESC";
        List<Employee> employees = new ArrayList<>();
        String term = "%" + (search == null ? "" : search.trim()) + "%";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 1; i <= 4; i++) ps.setString(i, term);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) employees.add(new Employee(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("phone"), rs.getString("department"),
                        rs.getString("position"), rs.getBigDecimal("salary")));
            }
        }
        return employees;
    }

    public void insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(name,email,phone,department,position,salary) VALUES(?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, e); ps.executeUpdate();
        }
    }

    public void update(int id, Employee e) throws SQLException {
        String sql = "UPDATE employees SET name=?,email=?,phone=?,department=?,position=?,salary=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, e); ps.setInt(7, id);
            if (ps.executeUpdate() == 0) throw new SQLException("Employee record was not found.");
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM employees WHERE id=?")) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) throw new SQLException("Employee record was not found.");
        }
    }

    public int count() throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM employees")) {
            rs.next(); return rs.getInt(1);
        }
    }

    private void fill(PreparedStatement ps, Employee e) throws SQLException {
        ps.setString(1, e.getName()); ps.setString(2, e.getEmail()); ps.setString(3, e.getPhone());
        ps.setString(4, e.getDepartment()); ps.setString(5, e.getPosition()); ps.setBigDecimal(6, e.getSalary());
    }
}
