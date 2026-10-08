package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Dashboard extends JFrame {
    private final EmployeeDAO dao = new EmployeeDAO();
    private final JTextField search = new JTextField(22);
    private final JLabel countLabel = new JLabel("Employees: —");
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Name", "Email", "Phone", "Department", "Position", "Salary"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);

    public Dashboard() {
        setTitle("Employee Management System | Dashboard"); setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 620); setMinimumSize(new Dimension(850, 500)); setLocationRelativeTo(null);
        JPanel root = new JPanel(new BorderLayout(12,12)); root.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        root.setBackground(new Color(244,247,251)); setContentPane(root);
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false);
        JLabel title = new JLabel("Employee Management Dashboard"); title.setFont(new Font("SansSerif",Font.BOLD,23));
        countLabel.setFont(new Font("SansSerif",Font.BOLD,14)); header.add(title,BorderLayout.WEST); header.add(countLabel,BorderLayout.EAST); root.add(header,BorderLayout.NORTH);
        table.setRowHeight(26); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); table.setAutoCreateRowSorter(true);
        root.add(new JScrollPane(table),BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout(8,8)); bottom.setOpaque(false);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Search name/email/department/position:")); searchPanel.add(search);
        JButton searchBtn = new JButton("Search"); JButton clearBtn = new JButton("Clear"); searchPanel.add(searchBtn); searchPanel.add(clearBtn);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT)); actions.setOpaque(false);
        JButton add = new JButton("Add Employee"); JButton edit = new JButton("Update Selected"); JButton delete = new JButton("Delete Selected"); JButton refresh = new JButton("Refresh");
        actions.add(add); actions.add(edit); actions.add(delete); actions.add(refresh);
        bottom.add(searchPanel,BorderLayout.NORTH); bottom.add(actions,BorderLayout.SOUTH); root.add(bottom,BorderLayout.SOUTH);
        searchBtn.addActionListener(e -> loadEmployees(search.getText())); search.addActionListener(e -> loadEmployees(search.getText()));
        clearBtn.addActionListener(e -> { search.setText(""); loadEmployees(""); });
        refresh.addActionListener(e -> loadEmployees(search.getText())); add.addActionListener(e -> showForm(null));
        edit.addActionListener(e -> editSelected()); delete.addActionListener(e -> deleteSelected());
        loadEmployees("");
    }
    private void loadEmployees(String query) {
        try {
            model.setRowCount(0); List<Employee> rows = dao.findAll(query);
            for (Employee e : rows) model.addRow(new Object[]{e.getId(),e.getName(),e.getEmail(),e.getPhone(),e.getDepartment(),e.getPosition(),e.getSalary()});
            countLabel.setText("Employees: " + dao.count());
        } catch (SQLException ex) { showDatabaseError(ex); }
    }
    private void showForm(Integer id) {
        JTextField name = new JTextField(22), email = new JTextField(22), phone = new JTextField(22), department = new JTextField(22), position = new JTextField(22), salary = new JTextField(22);
        if (id != null) {
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            name.setText(String.valueOf(model.getValueAt(row,1))); email.setText(String.valueOf(model.getValueAt(row,2)));
            phone.setText(String.valueOf(model.getValueAt(row,3))); department.setText(String.valueOf(model.getValueAt(row,4)));
            position.setText(String.valueOf(model.getValueAt(row,5))); salary.setText(String.valueOf(model.getValueAt(row,6)));
        }
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        form.add(new JLabel("Full name *")); form.add(name); form.add(new JLabel("Email *")); form.add(email);
        form.add(new JLabel("Phone")); form.add(phone); form.add(new JLabel("Department")); form.add(department);
        form.add(new JLabel("Position")); form.add(position); form.add(new JLabel("Salary *")); form.add(salary);
        int result = JOptionPane.showConfirmDialog(this,form,id == null ? "Add Employee" : "Update Employee",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;
        try {
            if (name.getText().trim().isEmpty() || email.getText().trim().isEmpty()) throw new IllegalArgumentException("Name and email are required.");
            if (!email.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) throw new IllegalArgumentException("Enter a valid email address.");
            BigDecimal pay = new BigDecimal(salary.getText().trim()); if (pay.signum() < 0) throw new IllegalArgumentException("Salary cannot be negative.");
            Employee employee = new Employee(name.getText().trim(),email.getText().trim(),phone.getText().trim(),department.getText().trim(),position.getText().trim(),pay);
            if (id == null) dao.insert(employee); else dao.update(id,employee);
            JOptionPane.showMessageDialog(this,id == null ? "Employee added successfully." : "Employee updated successfully."); loadEmployees(search.getText());
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this,"Salary must be a valid number.","Validation",JOptionPane.WARNING_MESSAGE); }
          catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(this,ex.getMessage(),"Validation",JOptionPane.WARNING_MESSAGE); }
          catch (SQLException ex) { showDatabaseError(ex); }
    }
    private void editSelected() {
        int row = table.getSelectedRow(); if (row < 0) { JOptionPane.showMessageDialog(this,"Select an employee first."); return; }
        showForm((Integer) model.getValueAt(table.convertRowIndexToModel(row),0));
    }
    private void deleteSelected() {
        int row = table.getSelectedRow(); if (row < 0) { JOptionPane.showMessageDialog(this,"Select an employee first."); return; }
        int modelRow = table.convertRowIndexToModel(row); int id = Integer.parseInt(String.valueOf(model.getValueAt(modelRow,0)));
        int confirm = JOptionPane.showConfirmDialog(this,"Delete employee ID " + id + "? This cannot be undone.","Confirm delete",JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) try { dao.delete(id); loadEmployees(search.getText()); }
        catch (SQLException ex) { showDatabaseError(ex); }
    }
    private void showDatabaseError(SQLException ex) {
        JOptionPane.showMessageDialog(this,"Could not access the database. Check that MySQL is running, the SQL script was imported, and DatabaseConnection.java has the correct password.\n\nDetails: " + ex.getMessage(),"Database error",JOptionPane.ERROR_MESSAGE);
    }
}
